<template>
  <div>
    <ul>
      <product v-for="product in products" :product="product" @onRemoveProduct="removeProduct"></product>
    </ul>
    <p v-if="!products.length">No products!</p>
  </div>
</template>

<script>
import Product from '@/components/Product.vue';

  export default {
    name: "ProductList",
    components: {
      Product
    },
    props: {
      products: {
        type: Array,
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
