<template>
    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                <div class="my-3 p-3 bg-white rounded shadow">
                    <h4 class="border-bottom border-gray pb-2 mb-0">Especialidades</h4>
                    <div class="media text-muted pt-3">
                        <div class="container mt-1">
                            <div class="row equal-height-cards">
                                <div class="col-lg-3">
                                    <div class="card text-center">
                                        <img src="@/assets/img/imagen1.jpg" class="card-img-top" alt="Imagen de ejemplo">
                                        <div class="card-body" style="min-height: 200px;">
                                            <h5 class="card-title">Cardiología</h5>
                                            <p class="card-text">
                                                La cardiología es la rama de la medicina que se encarga del estudio, diagnóstico y
                                                tratamiento de las enfermedades del corazón y del aparato circulatorio.
                                            </p>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                                                        <button class="btn btn-outline-warning me-md-2" type="button"><b-icon icon="pencil"></b-icon></button>
                                                        <button class="btn btn-outline-danger me-md-2" type="button"><b-icon icon="trash"></b-icon></button>
                                                        </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="card text-center">
                                        <img src="@/assets/img/imagen2.jpg" class="card-img-top" alt="Imagen de ejemplo">
                                        <div class="card-body" style="min-height: 200px">
                                            <h5 class="card-title">Radiología</h5>
                                            <p class="card-text">
                                                La radiología es la especialidad médica que se ocupa de generar imágenes del interior
                                                del cuerpo mediante diferentes agentes físicos (rayos X)
                                            </p>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                                                        <button class="btn btn-outline-warning me-md-2" type="button"><b-icon icon="pencil"></b-icon></button>
                                                        <button class="btn btn-outline-danger me-md-2" type="button"><b-icon icon="trash"></b-icon></button>
                                                        </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="card text-center">
                                        <img src="@/assets/img/imagen3.jpg" class="card-img-top" alt="Imagen de ejemplo">
                                        <div class="card-body" style="min-height: 200px">
                                            <h5 class="card-title">Neurología</h5>
                                            <p class="card-text">
                                                La neurología es la especialidad médica que trata los trastornos del sistema nervioso y el
                                                diagnóstico y tratamiento de todas las categorías de enfermedades.
                                            </p>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                                                        <button class="btn btn-outline-warning me-md-2" type="button"><b-icon icon="pencil"></b-icon></button>
                                                        <button class="btn btn-outline-danger me-md-2" type="button"><b-icon icon="trash"></b-icon></button>
                                                        </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3">
                                    <div class="card text-center">
                                        <img src="@/assets/img/imagen4.jpg" class="card-img-top" alt="Imagen de ejemplo">
                                        <div class="card-body" style="min-height: 200px">
                                            <h5 class="card-title">Traumatología</h5>
                                            <p class="card-text">
                                                La traumatología es la especialidad médica que se encarga del estudio de las lesiones del
                                                aparato locomotor. Tiene como objetivo el diagnóstico de lesiones fisicas.
                                            </p>
                                            <div class="row">
                                                <div class="col">
                                                    <div class="d-grid gap-2 d-md-flex justify-content-md-center">
                                                        <button class="btn btn-outline-warning me-md-2" type="button"><b-icon icon="pencil"></b-icon></button>
                                                        <button class="btn btn-outline-danger me-md-2" type="button"><b-icon icon="trash"></b-icon></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
    </template>
<script>
import DataView from 'primevue/dataview';

export default {
    data(){
        return {
            specialities: [
            {
                name: 'Cardiología',
                description: 'Especialidad que se encarga del estudio, diagnóstico y tratamiento de las enfermedades del corazón y del aparato circulatorio.',
                price: 2500,
                status: 'ACTIVE'
            },
            {
                name: 'Dermatología',
                description: 'Especialidad que se encarga del estudio, diagnóstico y tratamiento de las enfermedades de la piel.',
                price: 2500,
                status: 'ACTIVE'
            },

        ]
        }
    }
}
</script>

<style scoped>

body {
    padding-top: 56px;
}


label::after{
    content: " *";
    color: red;
}
</style>