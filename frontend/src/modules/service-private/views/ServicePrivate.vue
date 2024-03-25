<template>
    <div class="back">
        <h1>Nuestros Servicios</h1>
        <b-row>
            <b-col sm="6" md="4" lg="4" v-for="(service, index) in services" :key="index" class="mt-4">
                <Card class="custom-card">
                    <template #header>
                        <img :src="require(`@/assets/${service.image}`)" class="image" alt="">
                    </template>
                    <template #title>
                        <h2>{{ service.title }}</h2>
                    </template>
                    <template #content>
                        <p>{{ service.description }}</p>
                    </template>
                    <template #footer>
                       <Button icon="pi pi-pencil" class="p-button-rounded"></Button>
                       <Button icon="pi pi-trash" class="p-button-rounded p-button-secondary" ></Button>
                    </template>
                </Card>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import Card from 'primevue/card';
import Button from 'primevue/button';
export default {
    data() {
        return {
            services: [
                {
                    title: "Urgencias",
                    description: "Los servicios de urgencias tienen como misión la prestación de la atención sanitaria urgente a las personas que la demanden, en el tiempo adecuado.",
                    image: "img/urgencias.jpg"
                },
                {
                    title: "Intervenciones Quirúrgicas",
                    description: "Es la operación instrumental, total o parcial, de lesiones causadas por enfermedades o accidentes, con fines diagnósticos, de tratamiento o de rehabilitación de secuelas.",
                    image: "img/intervenciones.jpg"
                },
                {
                    title: "Atención del embarazo parto y puerperio.",
                    description: "El embarazo, parto y puerperio constituye un proceso por el que atraviesan las mujeres que deciden tener hijos a lo largo de su vida reproductiva.",
                    image: "img/parto.jpg"
                },
                {
                    title: "Unidad de Cuidados Especiales Neonatales.",
                    description: "Unidad Médica destinada a atender a todo recién nacido (0 a 28 días de vida) con cualquier proceso mórbido o enfermedad que ponga en peligro su vida.",
                    image: "img/cuidado.jpg"
                },
                {
                    title: "Vacunas",
                    description: " La vacunación es una forma sencilla, inocua y eficaz de protegernos contra enfermedades dañinas antes de entrar en contacto con ellas.",
                    image: "img/vacuna.jpg"
                },
                {
                    title: "Exámenes de audición y visión.",
                    description: "Las pruebas de audición miden qué tan bien escucha. Pueden ayudar a diagnosticar pérdida de la audición, qué tan severa es y qué parte de su audición no está funcionando bien.",
                    image: "img/vision.jpg"
                },

            ]
        };
    },
  components: {
    Card,
    Button
  }
};
</script>

<style scoped>
.custom-card {
    border: 1px solid #ccc;
    border-radius: 10px 10px !important;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    height: 100%;
    background-color: #fff;
}

.image {
    height: 300px !important;
    width: 95% !important;
    margin-top: 15px;
    border-radius: 10px 10px;
}

h1 {
    font-weight: bolder !important;
    font-family: Arial, Helvetica, sans-serif;
    color: black !important;
}

h2 {
    font-weight: bolder !important;
    font-family: Arial, Helvetica, sans-serif;
    margin-top: 30px !important;
    color: black !important;
}

p {
    font-family: Arial, Helvetica, sans-serif;
    font-weight: 300 !important;
    padding: 30px;
    font-size: larger !important;
    color: black !important;
}

.back {
    padding: 20px;
}

Button{
    margin: 30px;
    background-color: #2a715a;
    size: 50px!important;
}

</style>