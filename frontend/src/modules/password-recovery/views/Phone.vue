<template>
    <div id="background">
        <Card class="shadow-lg">
            <template #title>
                Recuperación por teléfono
            </template>
            <template #subtitle>
                Ingresa tu número de teléfono
            </template>
            <template #content>
                <div class="p-fluid grid">
                    <b-row>
                        <b-col cols="12" class=" mb-3" lg="12" sm="12">
                            <div class="field">
                                <span class="p-float-label p-input-icon-right" >
                                    <i class="pi pi-phone" />
                                    <InputText id="field-phone" type="text" />
                                    <label for="field-phone">Número de teléfono</label>
                                </span>
                            </div>
                        </b-col>
                    </b-row>
<!--                     <b-row>
                        <b-col cols="12" class="d-flex justify-content-center mt-4">
                            <Button class="p-button-rounded button-style w-75" label="Enviar código" />
                        </b-col>
                    </b-row> -->

                </div>
            </template>
        </Card>
    </div>
</template>

<script>
import Card from 'primevue/card/Card';
export default {
    name: 'Phone',
    components: {
        Card
    }
}
</script>

<style>

</style>