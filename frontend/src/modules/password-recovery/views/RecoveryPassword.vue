<template>
    <div>
        <Navbar v-if="this.user" />
        <NavbarPublic v-else="this.user" />
        <!-- <Sidebar v-if="this.user" /> -->
        <div class="background">
            <Header title="Recuperar Contraseña" />
            <div>
                <div class="d-flex justify-content-center align-items-center h-100">
                    <b-col cols="12" md="10" lg="4">
                        <!-- Card que pide el token  -->
                        <Card class="cards shadow-lg" v-if="this.writeCode">
                            <template #header>
                                <div class="d-flex justify-content-center align-items-center">
                                    <h4 class="m-1">Ingrese el código</h4>
                                </div>
                            </template>
                            <template #content>
                                <div class="p-fluid grid">
                                    <b-row>
                                        <b-col>
                                            <div class="d-flex justify-content-center">
                                                <img class="img-style" src="../../../assets/img/logo.png"
                                                    alt="hospital_logo">
                                            </div>
                                        </b-col>
                                    </b-row>
                                    <b-row>
                                        <b-col cols="12" class="mb-3 d-flex justify-content-center" lg="12">
                                            <div class="field w-75">
                                                <span class="p-float-label p-input-icon-right">
                                                    <i class="pi pi-eye" />
                                                    <InputText type="text" v-model="v$.token.$model" />
                                                    <label for="field-password">Ingrese el código que se le envió por
                                                        sms</label>
                                                </span>
                                            </div>
                                        </b-col>
                                        <b-col cols="12" class="d-flex justify-content-center mt-3">
                                            <Button class="p-button-rounded button-style w-75" label="Enviar código"
                                                @click="writePassword()" />
                                        </b-col>
                                        <b-col cols="12" class="d-flex justify-content-center mt-3">
                                            <Button label="Iniciar sesión" class="p-button-link w-75"
                                                @click="$router.push({ name: 'login' })" />
                                        </b-col>
                                    </b-row>
                                </div>
                            </template>
                        </Card>
                        <!-- Card para ingresar las 2 contraseñas -->
                        <Card class="cards shadow-lg" v-if="this.writePass">
                            <template #header>
                                <div class="d-flex justify-content-center align-items-center">
                                    <h4 class="m-1">Ingrese la nueva Contraseña</h4>
                                </div>
                            </template>
                            <template #content>
                                <div class="p-fluid grid">
                                    <b-row>
                                        <b-col>
                                            <div class="d-flex justify-content-center">
                                                <img class="img-style" src="../../../assets/img/logo.png"
                                                    alt="hospital_logo">
                                            </div>
                                        </b-col>
                                    </b-row>
                                    <b-row>
                                        <b-col cols="12" class="mb-3 d-flex justify-content-center" lg="12">
                                            <div class="field w-75">
                                                <span class="p-float-label p-input-icon-right">
                                                    <i class="pi pi-eye" />
                                                    <InputText type="pasword" v-model="v$.password.$model" />
                                                    <label for="field-password">Ingrese la nueva contraseña</label>
                                                </span>
                                            </div>
                                        </b-col>
                                        <b-col cols="12" class="mb-3 d-flex justify-content-center" lg="12">
                                            <div class="field w-75">
                                                <span class="p-float-label p-input-icon-right">
                                                    <i class="pi pi-eye" />
                                                    <InputText type="pasword" v-model="v$.password2.$model" />
                                                    <label for="field-password2">Vuelva a escribir la contraseña</label>
                                                </span>
                                            </div>
                                        </b-col>
                                        <b-col cols="12" class="d-flex justify-content-center mt-3">
                                            <Button label="Volver a enviar el código" class="p-button-link w-75"
                                                @click="sentNumber()" />
                                        </b-col>
                                        <b-col cols="12" class="d-flex justify-content-center mt-3">
                                            <Button class="p-button-rounded button-style w-75"
                                                label="Cambiar contraseña" @click="sentNewPass()" />
                                        </b-col>
                                    </b-row>
                                </div>
                            </template>
                        </Card>
                        <!-- Card que pide el número del usuario -->
                        <Card class="cards shadow-lg" v-if="this.writeNumber">
                            <template #header>
                                <div class="d-flex justify-content-center align-items-center">
                                    <h4 class="m-1">Ingresa tú número</h4>
                                </div>
                            </template>
                            <template #content>
                                <div class="p-fluid grid">
                                    <b-row>
                                        <b-col>
                                            <div class="d-flex justify-content-center">
                                                <img class="img-style" src="../../../assets/img/logo.png"
                                                    alt="hospital_logo">
                                            </div>
                                        </b-col>
                                    </b-row>
                                    <b-row>
                                        <b-col cols="12" class="mb-3 d-flex justify-content-center" lg="12">
                                            <div class="field w-75">
                                                <span class="p-float-label p-input-icon-right"
                                                    :class="{ 'invalid-field-custom': v$.phone.$error }">
                                                    <i class="pi pi-phone" />
                                                    <InputText type="number" v-model="v$.phone.$model" id="field-phone"
                                                        :useGrouping="false" />
                                                    <label for="field-phone">Número de
                                                        teléfono</label>
                                                </span>
                                                <div class="text-danger text-start pt-1">
                                                    <p class="error-messages"
                                                        v-if="v$.phone.$dirty && v$.phone.minLength.$invalid">
                                                        {{ v$.phone.minLength.$message }}
                                                    </p>
                                                    <p class="error-messages"
                                                        v-if="v$.phone.$dirty && v$.phone.maxLength.$invalid">
                                                        {{ v$.phone.maxLength.$message }}
                                                    </p>
                                                </div>
                                            </div>
                                        </b-col>
                                        <b-col cols="12" class="d-flex justify-content-center mt-3">
                                            <Button class="p-button-rounded button-style w-75" @click="sentNumber()"
                                                label="Enviar código" />
                                        </b-col>
                                        <b-col cols="12" class="d-flex justify-content-center mt-3">
                                            <Button v-if="!user" label="Iniciar sesión" class="p-button-link w-75"
                                                @click="$router.push({ name: 'login' })" />
                                            <Button v-if="user" label="Cancelar"
                                                class="p-button-rounded button-style w-75"
                                                @click="$router.push({ name: 'perfil' })" />
                                        </b-col>
                                    </b-row>
                                </div>
                            </template>
                        </Card>

                    </b-col>
                </div>
            </div>
            <Toast />
        </div>
        <FooterPublic v-if="!this.user" />
    </div>
</template>

<script>
import Card from 'primevue/card/Card';
import InputNumber from 'primevue/inputnumber';
import InputText from 'primevue/inputtext/InputText';
import { useVuelidate } from '@vuelidate/core'
import { required, helpers, minLength, maxLength } from '@vuelidate/validators'
import { reactive } from '@vue/composition-api'
import service from "@/modules/password-recovery/service/recovery-pass"
import { encrypt } from '@/config/security';
import Toast from 'primevue/toast';
import Navbar from '@/components/Navbar.vue';
import NavbarPublic from '@/components/NavbarPublic.vue';
import Sidebar from '@/components/Sidebar.vue';
import Header from '@/components/Header.vue';
import FooterPublic from '@/components/FooterPublic.vue'

export default {
    name: 'RecoveryPassword',
    components: {
        Card,
        InputNumber,
        Toast,
        Navbar,
        NavbarPublic,
        Sidebar,
        Header,
        FooterPublic
    },
    data() {
        return {
            isLoading: true,
            writeNumber: true,
            writePass: false,
            writeCode: false,
            user: false,
        }

    },
    mounted() {
        this.loggedUserExist();
    },
    setup() {
        const recovery = reactive({
            password: '',
            phone: '',
            password2: '',
            token: ''
        })

        const rules = {
            phone: {
                minLength: helpers.withMessage("El número debe de contar con maximo 10 digitos", minLength(10)),
                maxLength: helpers.withMessage("El número debe de contar con maximo 10 digitos", maxLength(10))
            },
            password: {
                required: helpers.withMessage("Debes de agregar una constraseña", required),
                minLength: helpers.withMessage("La contraseña debe de contar con mínimo 6 digitos", minLength(6)),
                maxLength: helpers.withMessage("La contraseña debe de contar con maximo 20 digitos", maxLength(20))
            },
            password2: {
                required: helpers.withMessage("Debes de agregar una constraseña", required),
                minLength: helpers.withMessage("La contraseña debe de contar con mínimo 6 digitos", minLength(6)),
                maxLength: helpers.withMessage("La contraseña debe de contar con maximo 20 digitos", maxLength(20))
            },
            token: {
                required: helpers.withMessage("Debes ingresar el token", required),
                minLength: helpers.withMessage("El token debe de contar con mínimo 5 carácteres", minLength(5)),
                maxLength: helpers.withMessage("El token debe de contar con maximo 5 carácteres", maxLength(5))
            },

        }
        const v$ = useVuelidate(rules, recovery)
        return { recovery, v$ }
    },
    methods: {
        async sentNumber() {
            if (this.recovery.phone !== "") {
                const new_data = {
                    phone: this.recovery.phone
                }
                const encoded = await encrypt(JSON.stringify(new_data));
                const { status } = await service.send_number(encoded);
                if (status === 200) {
                    this.$toast.add({ severity: 'success', summary: '¡Éxito!', detail: 'El código se envio exitosamente', life: 3000 });
                    this.validCode();
                } else {
                    this.$toast.add({ severity: 'warn', summary: '¡Ups!', detail: 'No se pudo enviar el código por sms', life: 3000 });
                }
            } else {

            }
        },

        validCode() {
            if (this.recovery.phone !== "") {
                this.writeCode = true;
                this.writeNumber = false;
                this.writePass = false;
            } else {

            }
        },

        writePassword() {
            if (this.recovery.token !== "") {
                this.writeCode = false;
                this.writeNumber = false;
                this.writePass = true;
            } else {

            }
        },
        async sentNewPass() {
            if (this.recovery.password && this.recovery.password2 !== "") {
                if (this.recovery.password === this.recovery.password2) {
                    const new_data = {
                        phone: this.recovery.phone,
                        password: this.recovery.password,
                        token: this.recovery.token,
                    }
                    const encoded = await encrypt(JSON.stringify(new_data));
                    const { status } = await service.send_number(encoded);
                    if (status === 200 || status === 201) {
                        this.$toast.add({ severity: 'success', summary: '¡Éxito!', detail: 'Contraseña restablecida con éxito', life: 3000 });
                    } else {
                        this.$toast.add({ severity: 'error', summary: '¡Cuidado!', detail: 'Tu contraseña no se pudo restablecer', life: 3000 });
                    }
                } else {
                    this.$toast.add({ severity: 'warn', summary: '¡Cuidado!', detail: 'Tus contraseñas no coinciden', life: 3000 });
                }
            }
        },
        async loggedUserExist() {
            const userExist = await service.exist_user();
            this.user = userExist;
        }

    }
}
</script>

<style scoped lang="scss">
@import "@/styles/colors.scss";

.background {
    height: 100%;
    justify-content: center;
    align-items: center;
    width: 100%;

}

.cards {
    width: 100%;
    height: 480px;
    border: 1px solid #ccc;
    border-radius: 10px 10px !important;
    padding: 10px;
    margin: 30px 0 30px 0;
}

.img-style {
    max-width: 200px !important;
    max-height: 200px;
}

.button-style {
    background-color: $green-primary;
    color: white;
    border: none;
}

.button-style {
    background-color: $green-primary;
    color: white;
    border: none;
}
</style>