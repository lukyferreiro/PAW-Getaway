export default interface UserModel {
    userId: number;
    name: string;
    surname: string;
    sub: string;   //sub is the email
    isVerified: boolean;
    isProvider: boolean;
    // token?: string;
    // url?: string;
    hasImage: boolean;
    profileImageUrl: string;
}
