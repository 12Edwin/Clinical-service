<template>
    <div class="background">
        <div class="d-flex justify-content-center align-items-center h-100">
            <b-col cols="12" md="10" lg="4">
                <Card class="cards shadow-lg">
                <template #header>
                    <div class="d-flex justify-content-center align-items-center" >
                        <h4 class="m-1">Recuperación de contraseña</h4>
                    </div>
                </template>
                <template #content>
                    <div class="p-fluid grid">
                        <b-row>
                            <b-col>
                                <div class="d-flex justify-content-center">
                                    <img class="img-style" src="../../../assets/img/logo.png" alt="hospital_logo">
                                </div>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col cols="12" class="mb-3 d-flex justify-content-center" lg="12">
                                <div class="field w-75">
                                    <span class="p-float-label p-input-icon-right" >
                                        <i class="pi pi-phone"/>
                                        <InputText id="field-phone" type="text"/>
                                        <label for="field-phone">Número de teléfono</label>
                                    </span>
                                </div>
                            </b-col>
                            <b-col cols="12" class="d-flex justify-content-center mt-3">
                                <Button class="p-button-rounded button-style w-75" label="Enviar código" />
                            </b-col>
                            <b-col cols="12" class="d-flex justify-content-center mt-3">
                                <Button label="Iniciar sesión" class="p-button-link w-75" @click="$router.push({name: 'login'})"/>
                            </b-col>
                        </b-row>
                    </div>
                </template>
        </Card>
            </b-col>
        </div>
    </div>
</template>

<script>
import Card from 'primevue/card/Card';
export default {
    name: 'RecoveryPassword',
    components: {
        Card
    },
    data() {
        return {
            isLoading: true
        }
    },
}
</script>

<style scoped>
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
    background-color: #2a715a;
    color: white;
    border: none;
}

.button-style:hover {
    background-color: rgb(42, 113, 90, 0.9);
}

</style>