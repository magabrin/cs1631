const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');

module.exports = {
  // entry: {
  //   app: './GuiWebserver/frontend/src/index.js',
  //   //print: './src/print.js'
  // },
  // devServer: {
  //     contentBase: './dist'
  // },
  // devtool: 'inline-source-map',
  // // plugins: [
  // //   new CleanWebpackPlugin(),
  // //   new HtmlWebpackPlugin({
  // //     title: 'Development'
  // //   })
  // // ],
  // output: {
  //   filename: '[name].bundle.js',
  //   path: path.resolve(__dirname , 'dist')
  // },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
        },
      },
      {
        test: /\.css$/,
        use: [
          'style-loader',
          'css-loader',
        ],
      },
    ],
  },
};
