<template>
    <div style="background-color: white;">
        <Menubar :model="navbarItems"
            style="background-color: white; justify-content: center; border: none; position: fixed; top: 0; width: 100%; z-index: 1000; font-weight: bold;" />
        <h1>Nuestros Servicios</h1>
        <div class="back">
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
                            <p>{{ service.price }}</p>
                        </template>
                    </Card>
                </b-col>
            </b-row>
        </div>
        <footer class="bg-white">
            <div class="text-center py-2 text-white" style="background-color: #2a715a;">
                <p style="font-weight: bolder!important; color: white!important;">© Hospital San J 2024. Todos los
                    derechos
                    reservados.</p>
            </div>
        </footer>
    </div>
</template>

<script>
import Card from 'primevue/card';
import Menubar from 'primevue/menubar';
import servicios from  '@/modules/service-private/service-services/Services'
export default {
    data() {
        return {
            services: [
                {
                    title: "Urgencias",
                    description: "Los servicios de urgencias tienen como misión la prestación de la atención sanitaria urgente a las personas que la demanden, en el tiempo adecuado.",
                    image: "img/urgencias.jpg",
                    price: "$4000"
                },
                {
                    title: "Intervenciones Quirúrgicas",
                    description: "Es la operación instrumental, total o parcial, de lesiones causadas por enfermedades o accidentes, con fines diagnósticos, de tratamiento o de rehabilitación de secuelas.",
                    image: "img/intervenciones.jpg",
                    price: "$4000"
                },
                {
                    title: "Atención del embarazo parto y puerperio.",
                    description: "El embarazo, parto y puerperio constituye un proceso por el que atraviesan las mujeres que deciden tener hijos a lo largo de su vida reproductiva.",
                    image: "img/parto.jpg",
                    price: "$4000"
                },
                {
                    title: "Unidad de Cuidados Especiales Neonatales.",
                    description: "Unidad Médica destinada a atender a todo recién nacido (0 a 28 días de vida) con cualquier proceso mórbido o enfermedad que ponga en peligro su vida.",
                    image: "img/cuidado.jpg",
                    price: "$4000"
                },
                {
                    title: "Vacunas",
                    description: " La vacunación es una forma sencilla, inocua y eficaz de protegernos contra enfermedades dañinas antes de entrar en contacto con ellas.",
                    image: "img/vacuna.jpg",
                    price: "$4000"
                },
                {
                    title: "Exámenes de audición y visión.",
                    description: "Las pruebas de audición miden qué tan bien escucha. Pueden ayudar a diagnosticar pérdida de la audición, qué tan severa es y qué parte de su audición no está funcionando bien.",
                    image: "img/vision.jpg",
                    price: "$4000"
                },
            ],
            navbarItems: [
                {
                    label: 'Hospital San J',
                    to: "/home",
                },
                {
                    label: 'Conócenos',
                },
                {
                    label: 'Blog',
                },
                {
                    label: 'Especialidades',
                },
                {
                    label: 'Servicios',
                    to: '/services'
                },
                {
                    label: 'Nuestros Doctores',
                    to: "/our_doctors"
                },
                {
                    label: "Directorio",
                },
                {
                    label: '',
                    icon: 'pi pi-fw pi-user',
                    to: "/login",
                    end: true // Esto alinea este elemento a la derecha
                },
            ],
        };
    },
    components: {
        Card,
        Menubar
    },
    mounted() {
        // this.getServices();
    },
    methods: {
        async getServices() {
            try {
                console.log("si entra");
                const { data, status } = await servicios.get_services();
                if (status === 200 || status === 201) {
                    console.log("aqui entra y aun no hay nada");
                    console.log(data);
                }

            } catch (error) {
                return error;
            }
        }
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

.image-carrusel {
    overflow: hidden;
    height: 500px;
    width: 76%;
}

@media only screen and (max-width: 768px) {
    .image-carrusel {
        height: 300px;
        width: 98%;
    }
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

.buttons {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 10px;
}
</style>