const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    output: {
        path: path.resolve(__dirname, 'build'),
        filename: 'static/js/[name].[contenthash].js',
        clean: true,
    },
    plugins: [
        new HtmlWebpackPlugin({
            title: 'Caching',
        }),
    ],
    module: {
        rules: [
            {
                test: /\.(png|jpe?g|gif|svg)$/i,
                type: 'asset/resource',
                generator: {
                    filename: 'images/[name].[contenthash].[ext]',
                },
            },
        ],
    },
    resolve: {
        extensions: [
            ".js",
            ".jsx",
            ".tsx",
            ".css",
            ".scss",
            ".gif",
            ".png",
            ".jpg",
            ".jpeg",
            ".svg",
        ],
    },
};