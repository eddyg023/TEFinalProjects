<template>
  <div class="container">
    <header>
      <h1>Teams</h1>
    </header>
    <button v-if="!showForm && $store.state.token != null" @click="showForm = true">Add New Team</button>
    <section class="form-section" v-if="showForm && $store.state.token != null">
      <form @submit.prevent="addTeam">
        <fieldset class="form-fieldset">
          <legend>Add New Team</legend>
          <label>
            <span>Team Name:</span>
            <input type="text" v-model="newTeamName" placeholder="Enter Team Name" required>
          </label>
          <label>
            <span>City: </span>
            <input type="text" v-model="newTeamCity" placeholder="Enter City" required>
          </label>
          <label>
            <span>State: </span>
            <input type="text" v-model="newTeamState" placeholder="Enter State" required>
          </label>
          <button type="submit" class="btn btn-primary">Add Team</button>
          <button type="button" class="btn btn-secondary" @click="cancelForm">Cancel</button>
        </fieldset>
      </form>
    </section>
    <section class="team-list">
      <article v-for="team in teams" :key="team.id" @click="showTeam(team.teamId)">
        <h2>{{ team.name }}</h2>
        <p>{{ team.city + ", " + team.state }}</p>
      </article>
    </section>
  </div>
</template>

<script>
import teamService from '@/services/TeamService';

export default {
  data(){
    return {
      teams: [],
      showForm: false,
      newTeamName: '',
      newTeamCity: '',
      newTeamState: ''
    };
  },
  created(){
    this.loadTeams();
  },
  methods: {
    showTeam(id){
      this.$router.push({ name: 'TeamDetail', params: {id: id} });
    },
    addTeam(){
      const newTeam = {
        name: this.newTeamName,
        city: this.newTeamCity,
        state: this.newTeamState,
        completed: false
      };

      teamService.addTeam(newTeam)
        .then(response => {
          this.newTeamName = '';
          this.newTeamCity = '';
          this.newTeamState = '';
          this.loadTeams();
          console.log(response.data);
          this.teams.push(response.data);
          this.showForm = false;
        }).catch(error => {
          console.error(error);
        });
    },
    loadTeams(){
      teamService.getTeams()
        .then(response => {
          this.teams = response.data;
        }).catch(error => {
          console.error(error);
        });
    },
    cancelForm() {
      this.showForm = false;
      this.newTeamName = '';
      this.newTeamCity = '';
      this.newTeamState = '';
    }
  }
};
</script>

<style scoped>
  header {
    background-color: #337ab7;
    padding: 20px;
    margin-bottom: 10px;
    border-bottom: 1px solid #ddd;
    border-radius: 10px 10px 0 0;
    text-align: center;
  }

  header h1 {
    color: #fff;
    font-size: 24px;
    margin: 0;
  }

  /* Form styles */
  .form-section {
    padding: 20px;
  }
  
  .form-fieldset legend {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
  }

  label {
    display: block;
    margin-bottom: 10px;
  }

  label span {
    display: block;
    margin-bottom: 5px;
  }

  input[type="text"] {
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 5px;
    width: 100%;
    margin-bottom: 10px;
  }

  button[type="submit"] {
    background-color: #337ab7;
    color: #fff;
    padding: 10px 20px;
    margin: 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  button:hover {
    background-color: #23527c;
  }

  button{
    background-color: #337ab7;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  /* Team list styles */
  .team-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    padding: 20px;
  }

  article {
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 10px;
    margin: 10px;
    cursor: pointer;
    width: calc(33.33% - 20px);
    text-align: center;
  }

  article:hover {
    background-color: #f7f7f7;
  }

  h2 {
    font-size: 18px;
    margin: 10px;
  }

  @media (max-width: 1024px) {
    nav {
      text-align: center;
    }

    .form-fieldset {
      flex-direction: column;
    }

    label {
      width: 100%;
    }

    input[type="text"], input[type="number"], select {
      width: 100%;
    }

    .team-list {
      flex-direction: column;
    }

    article {
      width: 100%;
      margin: 10px 0;
    }
  }

  @media (max-width: 425px) {
    nav {
      text-align: center;
    }
    .form-fieldset {
      padding: 10px;
    }

    label {
      margin-bottom: 5px;
    }

    input[type="text"], input[type="number"], select {
      padding: 5px;
      font-size: 14px;
    }

    button.btn.btn-primary {
      width: auto;
      padding: 10px 20px;
      
    }

    button[type="submit"] {
      padding: 5px 10px;
      font-size: 14px;
    }

    .team-list {
      padding: 10px;
    }

    article {
      padding: 5px;
      margin: 5px 0;
    }

    article h2 {
      font-size: 16px;
    }

    article p {
      font-size: 14px;
    }
  }
</style>