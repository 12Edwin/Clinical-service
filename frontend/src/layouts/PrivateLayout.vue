<template>
  <div>
    <Navbar/>
    <div class="d-flex">

      <Sidebar></Sidebar>
      <div class="content">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components:{
    Navbar: ()=> import("@/components/Navbar.vue"),
    Sidebar: ()=> import('@/components/Sidebar.vue')
  },
}
</script>

<style scoped>
  .content{
    padding-left: 30px;
    padding-top: 30px;
    padding-right: 30px;
    width: 100%;
    overflow: auto;
    height: 92vh;
  }
</style>