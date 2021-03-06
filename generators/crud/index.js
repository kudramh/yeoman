var Generator = require('yeoman-generator');
var chalk = require('chalk');
var fs = require('fs');
var os = require('os');
var path = require('path');
var dust = require('dustjs-linkedin');
var u = require('underscore');
var mkdirp = require('mkdirp');

dust.filters.capitalize = function(value) {
  return value.charAt(0).toUpperCase() + value.slice(1);
}
dust.filters.known = function(type) {
  switch (type) {
    case 'INTEGER': return "int";
    case 'DOUBLE':  return "double";
    case 'STRING':  return "String";
    default: return "null";
  }
}
var configuration;
var CRUDGenerator = module.exports = class extends Generator {

  prompting() {
    this.log('CRUD-CORE PROJECT BUILDING');
    chalkBanner();
    return this.prompt([{
      type    : 'input',
      name    : 'crudConfigFile',
      message : 'CRUD Configuration File',
      default : this.templatePath('config'+path.sep+'crud.json')
    },
    {
      type    : 'input',
      name    : 'packagePath',
      message : 'Package Path',
      default : 'com.citigroup.nga.crud'
    }, {
      type    : 'input',
      name    : 'imports',
      message : 'Import',
      default : 'com.citigroup.nga.commonscore.beans.RequestCrud'
    },
    {
      type    : 'input',
      name    : 'className',
      message : 'Pojo Class Name',
      default : 'Crud'
    },
    {
      type    : 'input',
      name    : 'classExtends',
      message : 'Class Extend',
      default : 'RequestCrud'
    }]).then((config) => {
      configuration=config;
      configuration.templatePath = this.templatePath();
      this.log(configuration);
      buildDirectories();
      buildPojos();
    });
  }
}

function buildDirectories(){
  var packageFolder = configuration.packagePath.replace(/\./g, path.sep);
  var srcDirs = ['beans', 'config', 'constants', 'controller', 'main'];
  var srcPath = 'src/main/java/' + packageFolder;
  var rsrPath = 'src/main/resources';
  var tstPath = 'src/test/java'
  var trsPath = 'src/test/resources'
  mkdirp.sync(srcPath); mkdirp.sync(rsrPath);
  mkdirp.sync(tstPath); mkdirp.sync(trsPath);
  srcDirs.forEach( (dir)=>{ mkdirp.sync(srcPath+path.sep+dir) });
  configuration.beansPath = srcPath + path.sep + 'beans';
}

function buildPojos() {
    var cruderTemplate = fs.readFileSync(configuration.templatePath+'/bean/pojo.dust', 'utf8');
    var compiled = dust.compile(cruderTemplate, 'cruder');
    dust.loadSource(compiled);

    var config = JSON.parse( fs.readFileSync(configuration.crudConfigFile, 'utf8') );

    let definitions = u.toArray(config.operations).map( (x) => {
      return {'id':x.id,'dialogIn':x.dialogIn, 'dialogOut':x.dialogOut };
    });
    definitions.forEach( (d) => {
          var definitionItems = [];
          //REQUEST
          definitionItems.push({
            "package": {"path": configuration.packagePath},
            "imports":  [configuration.imports],
            "class": {
              "name": 'Request'+configuration.className+d.id,
              "extends": configuration.classExtends,
              "attributes":u.toArray(d.dialogIn.fields)
                .filter((f) => { return f.mapOut.length>0; })
                .map((f) => { return { 'name':f.mapOut, 'type':f.type } })
            }
          });
          //RESPONSE
          d.dialogOut.forEach( (out) => {
            var classPreffix = (out.source.toUpperCase().indexOf('ERROR')>-1 ||
                                out.description.toUpperCase().indexOf('ERROR')>-1)
                               ?'ResponseError':'Response';
            definitionItems.push({
              "package": {"path": configuration.packagePath},
              "imports":  [configuration.imports],
              "class": {
                "name": classPreffix+configuration.className+d.id,
                "extends": configuration.classExtends,
                "attributes":u.toArray(out.fields)
                  .filter((f) => { return f.mapOut.length>0; })
                  .map((f) => { return { 'name':f.mapOut, 'type':f.type } })
              }
            });
          });
          //CRUDER TEMPLATE BUILDING
          definitionItems.forEach( (pojo) => {
            dust.render('cruder', pojo, function(err, out) {
              //console.log(pojo);
              //console.log(out);
              var classFileName = configuration.beansPath+path.sep+pojo.class.name+'.java';
              fs.writeFile(classFileName, out, 'UTF-8', (error) => {
                if(error){ console.log(error) } else { console.log('Class generated:', classFileName); }
              });
            });
          });
    });
}

function chalkBanner (){
  console.log(chalk.green(
  '\n.............DD88888888888888888,............\n' +
    '...........:888888888888888888888,...........\n' +
    '..........+88888888888888888888888+..........\n' +
    '.........,8888888888888888888888888..........\n' +
    '.........888888888888...888888888888.........\n' +
    '.......,88888887..D88...88Z..88888888,.......\n' +
    '.......8888888,...888...88D...=8888888.......\n' +
    '......D888888,..$8888...88887...8888888......\n' +
    '.....Z888888$..I88888...88888:..88888888,....\n' +
    '....D8888888...888888...88888D..,88888888....\n' +
    '....88888888,..888888..,888888...88888888....\n' +
    '....88888888,..8888888$888888D..,88888888....\n' +
    '....88888888I..Z8888888888888+..888888888....\n' +
    '.....Z8888888...O888888888888..,88888888.....\n' +
    '......88888888...,88888888D...,88888888......\n' +
    '.......88888888=.....?I+.....I88888888.......\n' +
    '.......,88888888D7.........ZD88888888,.......\n' +
    '.........888888888888888888888888888.........\n' +
    '.........,8888888888888888888888888..........\n' +
    '..........+88888888888888888888888+..........\n' +
    '...........,888888888888888888888:...........\n' +
    '.............DD888888888888888DD.............\n' +
    chalk.yellow('\nKernel CRUD Generator')));
}
