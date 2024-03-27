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
  }
  
  export async function encrypt(plaintext) {
    try {
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
      // Codificar la cadena encriptada en Base64
      const base64Encoded = btoa(String.fromCharCode(...new Uint8Array(encrypted)));
      // Codificar para URL
      return encodeURIComponent(base64Encoded);
    } catch (error) {
      console.error("Error en la función encrypt:", error);
      throw error;
    }
  }
  
  export async function decrypt(ciphertext) {
    try {
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
      // Decodificar la cadena encriptada de URL
      const base64Decoded = decodeURIComponent(ciphertext);
      // Decodificar la cadena encriptada en Base64
      const encryptedData = Uint8Array.from(atob(base64Decoded), c => c.charCodeAt(0));
      const decrypted = await window.crypto.subtle.decrypt(
        {
          name: "AES-CBC",
          iv,
        },
        key,
        encryptedData
      );
      return decoder.decode(decrypted);
    } catch (error) {
      console.error("Error en la función decrypt:", error);
      throw error;
    }
  }
  