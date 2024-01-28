<template>
  <div class="sidebar" :class="{'side-close': closeSide}">
    <div class="w-100 d-flex justify-content-end mb-3 pe-3">
      <Button @click="toggleSidebar()" class="p-button-rounded px-2 p-button-success p-button-outlined"> <b-icon :class="{'toggle-arrow': closeSide}" class="arrow-side" icon="arrow-left-circle" scale="2"/> </Button>
    </div>
    <div class="dock-window dock-advanced" :class="[{'fade-dock': !closeSide}, {'unFade-dock': closeSide}]">
      <Dock :model="items" position="left">
        <template #item="slotProps">
          <a href="#" class="p-dock-action" @click="()=> $router.push({name: slotProps.item.route})">
            <Button class="p-button-rounded p-button-primary p-button-outlined" pill> <BIcon :icon="slotProps.item.icon"/></Button>
          </a>
        </template>
      </Dock>
    </div>
    <div class="w-100" :class="[{'fade-dock': closeSide}, {'unFade-dock': !closeSide}]">
      <ul>
        <li class="item" v-for="(item, ind) in items" :key="ind" @click="()=> $router.push({name: item.route})">
          <BIcon :icon="item.icon" scale="1.15"/> <span class="ms-3">{{item.label}}</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  components:{
    Dock: () => import('primevue/dock')
  },
  data(){
    return{
      closeSide: false,
      items:[
        {
          label: 'Doctores',
          icon: `trash`,
          route: 'doctors'
        },
        {
          label: 'Citas',
          icon: 'box',
          route: 'appoints'
        },
        {
          label: 'Áreas',
          icon: 'building',
          route: 'areas'
        },
        {
          label: 'Historial clínico',
          icon: 'heart',
          route: 'clinical-history'
        },
        {
          label: 'Tratamientos',
          icon: 'alarm',
          route: 'treatments'
        },
      ]
    }
  },
  methods:{
    toggleSidebar(){
      this.closeSide = !this.closeSide
    }
  }
}
</script>

<style scoped>
  .sidebar{
    height: 92vh;
    width: 14em;
    box-shadow: 5px 2px 20px 1px rgba(0, 0, 0, 0.2);
    border-radius: 5px;
    transition: all ease-out 0.8s;
    background-color: aliceblue;
    position: relative;
    padding-top: 50px;
  }
  .side-close{
    width: 6rem;
  }
  .fade-dock{
    animation-name: hideIcons;
    animation-duration: 1s ;
    animation-fill-mode: forwards;
  }

  .unFade-dock{
    animation-name: showIcons;
    animation-duration: 1s ;
    animation-fill-mode: forwards;
  }

  @keyframes hideIcons {
    from{
      opacity: 100%;
    }
    to{
      opacity: 0;
      visibility: hidden;
    }
  }

  @keyframes showIcons {
    to{
      opacity: 100%;
    }
    from{
      opacity: 0;
      visibility: hidden;
    }
  }

  .arrow-side{
    transform: rotate(0);
    transition: transform ease-out 1s;
  }

  .toggle-arrow{
    transform: rotate(180deg);
  }

  div ul{
    padding: 0;
  }

  .item{
    display: flex;
    align-items: center;
    margin: 1px 1px;
    text-decoration: none;
    padding-bottom: 18px;
    padding-top: 18px;
    padding-left: 15px;
    width: 100%;
    background-color: aliceblue;
    border-radius: 5px;
    box-shadow: 0 2px 20px 1px rgba(0, 0, 0, 0.2);
    transform: scale(1);
    transition: all ease-out 300ms;
    color: #6b8ebd;
  }

  .item:hover{
    background-color: #ffffff;
    transform: scale(1.05);
    cursor: pointer;
  }
</style>

<style>

</style>