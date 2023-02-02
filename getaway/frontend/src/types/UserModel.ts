export default interface UserModel {
    userId: number;
    name: string;
    surname: string;
    email: string;
    favsCount: number;
    verified: boolean;
    provider: boolean;
    token?: string;
}
