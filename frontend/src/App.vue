<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style>
#app {

  text-align: center;
  background-color: #e1e1e7;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}


</style>