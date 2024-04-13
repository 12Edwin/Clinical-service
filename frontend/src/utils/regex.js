export const words  = /^[A-Za-zÀ-ÿ\s]+$/
export const text = /^[\wáéíóúÁÉÍÓÚñÑüÜ.,;:?!¡¿'\- ]+$/
export const errorMessage = 'Caracteres no válidos'
export const curp = /^([A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[HM]{1}(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)[B-DF-HJ-NP-TV-Z]{3}[0-9A-Z]{1}[0-9]{1})+$/
export const rfc = /^[([A-ZÑ]{3,4}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]$/
export const dateFormatRegex = /^(0[1-9]|[1-2][0-9]|3[01])-(0[1-9]|1[0-2])-\d{4}$/;

export const acceptedFormatsForImages = ['.png', '.jpeg', '.jpg'];

export const newregex = /^[a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+( [a-zA-ZñÑáéíóúÁÉÍÓÚäëïöüÄËÏÖÜ0-9]+)*$/

export const formatDate = "YYYY-MM-DDTHH:mm:ss"
export const formatDate2 = "YYYY-MM-DD"
export const formatTime = "HH:mm"
export const format12Time = "hh:mm a"