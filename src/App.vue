<template>
  <div id="app">
    <div class="container">
      <h2>Productos</h2>
      <sort-products :products="products"></sort-products>
      <product-list :products="products" @onRemoveProduct="handleRemoveProduct"></product-list>
      <add-product @onAddProduct="handleAddProduct"></add-product>
    </div>
  </div>
</template>

<script>
import ProductList from './components/ProductList';
import AddProduct from './components/AddProduct';
import SortProducts from './components/SortProducts';

export default {
  name: 'app',
  components: {
    ProductList,
    AddProduct,
    SortProducts
  },
  computed: {
    products() {
      return this.$store.state.products;
    }
  },
  methods: {
    handleAddProduct(product) {
      this.$store.dispatch('addProduct', product);
    },

    handleRemoveProduct(product) {
      this.$store.dispatch('removeProduct', product);
    },
    async initData() {
      let products = await fetch('products.json').then(data => data.json());
      this.$store.dispatch('initData', products);
    }    
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
