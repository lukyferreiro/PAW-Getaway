
//TODO ver como poder hacer el hash del contenido

// Obtiene la extensión del archivo a partir de su nombre
// import * as fs from "fs";
//
// const getFileExtension = (fileName) => {
//     const lastDotIndex = fileName.lastIndexOf('.');
//     return (lastDotIndex === -1) ? '' : fileName.substr(lastDotIndex);
// };
//
// const getFileContent = (path) => {
//     return new Promise((resolve, reject) => {
//         fs.readFile(path, 'utf8', (err, data) => {
//             if (err) {
//                 reject(err);
//             } else {
//                 resolve(data);
//             }
//         });
//     });
// };
//
// const generateFileHash = (content) => {
//     return crypto.createHash('sha256').update(content).digest('hex');
// };
//
// export const getResourcePath = (path) => {
//     const fileContent = getFileContent(path);
//     const fileHash = generateFileHash(fileContent);
//
//     const fileName = path.substring(path.lastIndexOf('/') + 1);
//     const fileExtension = getFileExtension(fileName);
//     const filePathWithoutExtension = path.substring(0, path.lastIndexOf('.'));
//
//     return `${filePathWithoutExtension}.${fileHash}${fileExtension}`;
// };

export const getResourcePath = (path) => {
    // return path +'?v={hash}'
    return path
}