// Represents some generic auth provider API
const internalAuthProvider = {
    isAuthenticated: false,
    signIn(callback: VoidFunction) {
        internalAuthProvider.isAuthenticated = true
        setTimeout(callback, 100) // fake async
    },
    signOut(callback: VoidFunction) {
        internalAuthProvider.isAuthenticated = false
        setTimeout(callback, 100)
    },
};

export {internalAuthProvider};
