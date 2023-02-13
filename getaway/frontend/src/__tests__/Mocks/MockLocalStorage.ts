export class MockLocalStorage {
    private store: any;
    readonly length: number = 1;

    constructor() {
        this.store = {};
    }

    clear() {
        this.store = {};
    }

    key(index: number): string | null {
        return this.store[index];
    }

    getItem(key: string) {
        return this.store[key] || null;
    }

    setItem(key: string, value: string) {
        this.store[key] = String(value);
    }

    removeItem(key: string) {
        delete this.store[key];
    }
}

global.localStorage = new MockLocalStorage();
test("", () => {
});
