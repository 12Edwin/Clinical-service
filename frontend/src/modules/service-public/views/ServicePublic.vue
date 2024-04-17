<template>
    <div class="w-100">
        <h1>Nuestros Servicios</h1>
        <div class="w-100" style="display: flex; align-items: center; justify-content: center;">
            <div class="w-100" style=" margin-top: 50px;">
                <Carousel style="margin-top: 20px;" :value="services" :numVisible="1" :numScroll="1"
                    :responsiveOptions="responsiveOptions" :autoplayInterval="3000">
                    <template #header>
                    </template>
                    <template #item="slotProps">
                            <div class="w-100">
                                <div class="w-100">
                                    <img src="https://picsum.photos/600/300/?image=25" class="image-carrusel"
                                        :alt="slotProps.data.title" />
                                </div>
                                <div style="padding-left: 10%; padding-right: 10%;">
                                    <h3>
                                        {{ slotProps.data.name }}
                                    </h3>
                                    <div style="font-size: larger;">
                                        {{ slotProps.data.description }}
                                    </div>
                                    <div style="font-size: larger;">
                                        Especialidad: {{ slotProps.data.speciality.name }}
                                    </div>
                                    <div style="font-weight: bolder; font-size: larger;">
                                        ${{ slotProps.data.price }}
                                    </div>
                                </div>
                              </div>
                    </template>
                </Carousel>
            </div>
        </div>
      <div class="text-center py-2 text-white" style="background-color: #2a715a;">
        <p style="font-weight: 600 !important;">© Hospital San J 2024. Todos los derechos reservados.</p>
      </div>
    </div>
</template>

<script>
import Card from 'primevue/card';
import Menubar from 'primevue/menubar';
import servicios from '@/modules/service-private/service-services/Services'
import { decrypt } from "@/config/security"
import Carousel from 'primevue/carousel'
import CarouselItem from 'primevue/carousel/Carousel'
export default {
    components: {
        Card,
        Menubar,
        Carousel,
        CarouselItem
    },
    data() {
        return {
            services: [],
            responsiveOptions: [
                {
                    breakpoint: '1024px',
                    numVisible: 3,
                    numScroll: 3
                },
                {
                    breakpoint: '600px',
                    numVisible: 2,
                    numScroll: 2
                },
                {
                    breakpoint: '480px',
                    numVisible: 1,
                    numScroll: 1
                }
            ],
            activeIndex: 0,
        };
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
                const { status, data: { result } } = await servicios.getPublicServices(this.pageable)
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

.image-carrusel {
    height: 500px;
    width: 75%;
}

@media only screen and (max-width: 768px) {
    .image-carrusel {
        height: 300px;
        width: 98%;
    }
}

h1 {
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
    font-size: larger !important;
}

</style>