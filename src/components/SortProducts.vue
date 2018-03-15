<template>
    <button class="btn btn-primary" v-on:click="sortProducts(products)">Sort elements</button>
</template>

<script>
  export default {
    name: "SortProducts",
    props: {
      products: {
        type: Array,
      }
    },
    methods: {
      sortProducts(products) {
          this.products.sort(this.comparator);
      },
      comparator(a, b) {
          return a - b;
      }
    }
  }
</script>

<style scoped>

</style>
