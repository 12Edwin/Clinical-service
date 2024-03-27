// Esta función genera una clave criptográfica utilizando el algoritmo SHA-256
// a partir de un secreto definido, y devuelve los primeros 16 bytes de la clave resultante.
const getKey = async () => {
    const secretKey = 'SECRETO1';
    const encoder = new TextEncoder();
    const hashedKey = await window.crypto.subtle.digest(
        {
            name: "SHA-256",
        },
        encoder.encode(secretKey)
    );
    return new Uint8Array(hashedKey).slice(0, 16);
};

// Esta función realiza la operación de cifrado de un texto utilizando el algoritmo AES-CBC
// con una clave generada mediante la función getKey(). Devuelve el texto cifrado en formato base64.
export async function encrypt(plaintext) {
    const encoder = new TextEncoder();
    const keyMaterial = await getKey();
    const key = await window.crypto.subtle.importKey(
        "raw",
        keyMaterial,
        { name: "AES-CBC" },
        false,
        ["encrypt"]
    );
    const iv = new Uint8Array(16);
    const encrypted = await window.crypto.subtle.encrypt(
        {
            name: "AES-CBC",
            iv,
        },
        key,
        encoder.encode(plaintext)
    );
    return base64UrlEncode(String.fromCharCode.apply(null, new Uint8Array(encrypted)));
}

// Esta función realiza la operación de descifrado de un texto cifrado en formato base64
// utilizando el algoritmo AES-CBC y una clave generada mediante la función getKey().
// Devuelve el texto descifrado.
export async function decrypt(ciphertext) {

    const decoder = new TextDecoder();
    const keyMaterial = await getKey();
    const key = await window.crypto.subtle.importKey(
        "raw",
        keyMaterial,
        { name: "AES-CBC" },
        false,
        ["decrypt"]
    );
    const iv = new Uint8Array(16);
    const encryptedData = Uint8Array.from(atob(ciphertext), c => c.charCodeAt(0));
    const decrypted = await window.crypto.subtle.decrypt(
        {
            name: "AES-CBC",
            iv,
        },
        key,
        encryptedData
    );
    return decoder.decode(decrypted);
}


function base64UrlEncode(input) {
    const base64 = window.btoa(input);
    const base64Url = base64.replace(/\+/g, '-').replace(/\//g, '_');
    const base64UrlWithoutPadding = base64Url.replace(/=+$/, '');
    return base64UrlWithoutPadding;
}
