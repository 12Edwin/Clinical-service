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
                            <img  src="https://picsum.photos/600/300/?image=25" class="image" alt="">
                        </template>
                        <template #title>
                            <h2>{{ service.name }}</h2>
                        </template>
                        <template #content>
                            <p>{{ service.description }}</p>
                            <p style="font-weight: bolder!important">${{ service.price }}</p>
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
import servicios from '@/modules/service-private/service-services/Services'
import {decrypt} from "@/config/security"
export default {
    data() {
        return {
            services: [],
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
        this.pagination()
    },
    methods: {
        async pagination(event) {
            if (event != undefined) {
                const { page, rows } = event;
                this.pageable.page = page;
                this.pageable.size = rows;
            }
            try {
                const { status, data: { result } } = await servicios.get_services(this.pageable)
                if (status === 200 || status === 201) {
                    const decripted = await decrypt(result)
                    const { content, totalElements } = JSON.parse(decripted)
                    this.totalRecords = totalElements
                    this.services = content
                }
            } catch (error) { }

        },
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