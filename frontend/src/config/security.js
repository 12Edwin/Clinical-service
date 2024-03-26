// Define la clave aquí (debería ser la misma que la definida en el backend)
const secretKey = 'SECRETO1';

// Función para generar la clave secreta
async function generateSecretKey() {
    const encoder = new TextEncoder();
    const keyMaterial = await window.crypto.subtle.digest(
        {
            name: "SHA-256",
        },
        encoder.encode(secretKey)
    );
    return await window.crypto.subtle.importKey(
        "raw",
        new Uint8Array(keyMaterial),
        { name: "AES-CBC" },
        false,
        ["encrypt", "decrypt"]
    );
}

// Función para generar un vector de inicialización aleatorio
function generateRandomIV() {
    return window.crypto.getRandomValues(new Uint8Array(16));
}

// Función para codificar un ArrayBuffer en base64 URL-safe
function base64UrlEncode(arrayBuffer) {
    const uint8Array = new Uint8Array(arrayBuffer);
    const base64String = btoa(String.fromCharCode.apply(null, uint8Array));
    return base64String.replace(/\+/g, '-').replace(/\//g, '_').replace(/=/g, '');
}

// Función para decodificar una cadena base64 URL-safe en un ArrayBuffer
function base64UrlDecode(base64UrlString) {
    const base64String = base64UrlString.replace(/-/g, '+').replace(/_/g, '/');
    const binaryString = atob(base64String);
    const uint8Array = new Uint8Array(binaryString.length);
    for (let i = 0; i < binaryString.length; i++) {
        uint8Array[i] = binaryString.charCodeAt(i);
    }
    return uint8Array.buffer;
}

// Función para desencriptar los datos
export async function decrypt(ciphertext) {
    try {
        const key = await generateSecretKey();
        const iv = generateRandomIV();
        const encryptedData = base64UrlDecode(ciphertext);
        const decrypted = await window.crypto.subtle.decrypt(
            { name: "AES-CBC", iv: iv },
            key,
            encryptedData
        );
        const decoder = new TextDecoder();
        return decoder.decode(decrypted);
    } catch (error) {
        console.error(error);
        throw new Error("Decryption failed.");
    }
}
