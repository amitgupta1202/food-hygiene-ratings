const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require("extract-text-webpack-plugin");

const source = path.resolve(__dirname, 'src/main/webapp');
const destination = path.resolve(__dirname, 'src/main/webapp');

module.exports = {
    context: __dirname + '/app',
    entry: './app.js',
    output: {
        path: destination,
        filename: 'bundle.[hash].js'
    },
    devtool: 'source-map',
    module: {
        loaders: [
            {test: /\.js?$/, exclude: '/node_modules', loader: 'babel'},
            {test: /\.css$/, loader: ExtractTextPlugin.extract("style-loader", "css-loader")},
            {test: /\.eot(\?v=\d+\.\d+\.\d+)?$/, loader: "file"},
            // {test: /\.(woff|woff2)$/, loader: "url?prefix=font/&limit=5000"},
            {test: /\.woff(\?v=\d+\.\d+\.\d+)?$/, loader: "url?limit=10000&mimetype=application/font-woff"}, 
            {test: /\.woff2(\?v=\d+\.\d+\.\d+)?$/,loader: "url?limit=10000&mimetype=application/font-woff"},
            {test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, loader: "url?limit=10000&mimetype=application/octet-stream"},
            {test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, loader: "url?limit=10000&mimetype=image/svg+xml"},
            {test: /\.html$/, exclude: '/node_modules', loader: "html"}
        ]
    },
    plugins: [
        new ExtractTextPlugin('[name].[hash].css'),
        new HtmlWebpackPlugin({
            template: 'index.html',
            inject: 'body'
        })
    ]
};