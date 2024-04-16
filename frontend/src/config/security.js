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
            {name: "AES-CBC"},
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
    }catch (err){
        console.log("Error de encruptacion")
    }
}

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
  const base64Decoded = decodeURIComponent(ciphertext.replace(/\-/g, '+').replace(/_/g, '/'));
  const encryptedData = Uint8Array.from(window.atob(base64Decoded), c => c.charCodeAt(0));
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
