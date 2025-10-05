<template>
  <div id="app">
    <h1>Sistema de Gerenciamento de Usuários</h1>
    
    
    <div v-html="welcomeMessage"></div>
    
    <div class="search-section">
      <h2>Buscar Usuários</h2>
      <input v-model="name" placeholder="Digite o nome" @keyup.enter="search"/>
      <button @click="search">Buscar</button>
      
     
      <div v-if="searchMessage" v-html="searchMessage"></div>
      
      <ul>
        <li v-for="user in users" :key="user">{{ user }}</li>
      </ul>
    </div>

    <div class="create-section">
      <h2>Criar Usuário</h2>
      <input v-model="newUser.username" placeholder="Username"/>
      <input v-model="newUser.password" type="password" placeholder="Senha"/>
      <input v-model="newUser.email" placeholder="Email"/>
      <button @click="createUser">Criar</button>
      <div v-if="createMessage" v-html="createMessage"></div>
    </div>

    <div class="admin-section">
      <h2>Área Admin</h2>
      
      <button @click="getAdminData">Acessar Admin</button>
      <pre v-if="adminData">{{ adminData }}</pre>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: '',
      users: [],
      welcomeMessage: '<p>Bem-vindo ao sistema!</p>',
      searchMessage: '',
      createMessage: '',
      newUser: {
        username: '',
        password: '',
        email: ''
      },
      adminData: null,
      
      apiKey: 'sk-1234567890abcdef',
      adminToken: 'admin123'
    }
  },
  methods: {
    async search() {
      
      const url = `http://localhost:8080/users/search?name=${this.name}`;
      
      try {
        const res = await fetch(url);
        this.users = await res.json();
        
        this.searchMessage = `<p>Resultados para: <b>${this.name}</b></p>`;
      } catch (error) {
        
        console.log('API Key:', this.apiKey); // Log de credencial
        this.searchMessage = `<p style="color: red">Erro: ${error.message}</p>`;
      }
    },
    
    async createUser() {
      
      try {
        const res = await fetch('http://localhost:8080/users/create', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            
            'X-API-Key': this.apiKey
          },
          body: JSON.stringify(this.newUser)
        });
        
        const data = await res.json();
        
        this.createMessage = `<p>${data.message}</p>`;
        
        
        console.log('User created:', this.newUser.username, 'Password:', this.newUser.password);
      } catch (error) {
        this.createMessage = `<p style="color: red">Erro: ${error.message}</p>`;
      }
    },
    
    async getAdminData() {
      
      const url = `http://localhost:8080/users/admin/1?token=${this.adminToken}`;
      
      try {
        const res = await fetch(url);
        this.adminData = await res.json();
        
        console.log('Admin data:', this.adminData);
        localStorage.setItem('adminToken', this.adminToken); 
      } catch (error) {
        console.error('Error:', error);
      }
    }
  },
  mounted() {
    
    const urlParams = new URLSearchParams(window.location.search);
    const msg = urlParams.get('message');
    if (msg) {
      
      this.welcomeMessage = `<p>${msg}</p>`;
    }
    document.cookie = "token=abc123; path=/";

  }
}
</script>

<style>
.search-section, .create-section, .admin-section {
  margin: 20px 0;
  padding: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

input {
  margin: 5px;
  padding: 8px;
}

button {
  margin: 5px;
  padding: 8px 15px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
