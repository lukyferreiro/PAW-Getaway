export default interface UserModel {
    id: number;
    name: string;
    surname: string;
    email: string;
    verified: boolean;
    provider: boolean;
    token?: string;
    url?: string;
    hasImage: boolean;
}
