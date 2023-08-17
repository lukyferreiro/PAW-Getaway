export default interface CurrentUserModel {
    userId: number;
    name: string;
    surname: string;
    sub: string;   //sub is the email
    isVerified: boolean;
    isProvider: boolean;
    hasImage: boolean;
    profileImageUrl: string;
}
