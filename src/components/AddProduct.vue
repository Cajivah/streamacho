<template>
  <form @submit.prevent="onSubmit()">
    <input
      class="form-control"
      name="product"
      placeholder="product"
      v-model="newProduct.name"
      v-validate="'required|min:3'"
    >
    <input
      class="form-control"
      name="date"
      placeholder="date"
      v-model="newProduct.date"
      v-validate="'required|date_format:dd-MM-yyyy'"
    >
    <button class="btn btn-primary">Add</button>
    <div class="alert alert-danger" v-show="errors.has('product') | errors.has('date')">
      {{ errors.first('product') }}
      {{ errors.first('date') }}
    </div>
  </form>
</template>

<script>
  import uuid from 'uuid/v4';

  export default {
    name: "AddProduct",
    data() {
      return {
        newProduct: {
          name: '',
          date: new Date()
        }
      }
    },
    methods: {
      onSubmit() {
        this.$validator.validateAll().then(result => {
          if (!result) {
            return;
          }
          this.$emit('onAddProduct', {
            id: uuid(),
            ...this.newProduct
          });
          this.newProduct.name = '';
          this.newProduct.date = new Date();
          this.$validator.reset();
        });
      },
      formatDate(date) {
        
      }
    }
  }
</script>

<style scoped>

</style>
