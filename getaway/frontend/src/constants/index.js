
//TODO check que onda estas variables... tienne que salir de un .env
export const baseURL = process.env.baseURL
export const resourcePrefix = process.env.resourcePrefix

export const getResourcePath = (path) => {
    return resourcePrefix + path +'?v='+ process.env.ts
}