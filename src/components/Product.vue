<template>
      <li class="row">{{ product.name }}, date: {{ product.date}} <button class="btn btn-danger" v-on:click="removeProduct(product)">Remove</button></li>
</template>

<script>
  export default {
    name: "Product",
    props: {
      product: {
        type: Object,
      }
    },
    methods: {
      removeProduct(product) {
        this.$emit('onRemoveProduct', product);
      }
    }
  }
</script>

<style scoped>

</style>
