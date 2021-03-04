const path = require("path");

function resolveSrc(_path) {
  return path.join(__dirname, _path);
}
// vue.config.js
module.exports = {
  lintOnSave: true,
  outputDir:
    "D:/SSAFY/workspaceBoot/HappyHouse_final/src/main/resources/static",
  indexPath: "./index.html",
  configureWebpack: {
    // Set up all the aliases we use in our app.
    resolve: {
      alias: {
        assets: resolveSrc("src/assets")
      }
    }
  },
  css: {
    // Enable CSS source maps.
    sourceMap: process.env.NODE_ENV !== "production"
  }
};
