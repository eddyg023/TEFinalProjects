<template>
  <article v-if="team" class="team-profile">
    <header>
      <h1>{{team.name }}</h1>
      <p>{{ team.city + ", " + team.state }}</p>
    </header>

    <button v-if="!showForm && $store.state.token != null" @click="showForm = true" class="updateButton">Update Team</button>
        <button v-if="$store.state.token != null" @click="deleteTeam" class="deleteButton">Delete Team</button>
  
    <section class="form-section" v-if="showForm && $store.state.token != null">
      <form @submit.prevent="updateTeam">
        <fieldset class="form-fieldset">
          <legend>Update Team</legend>
          <label>
            <span>Team Name:</span>
            <input type="text" v-model="newTeamName" placeholder="Enter team name" >
          </label>
          <label>
            <span>Team City:</span>
            <input type="text" v-model="newTeamCity" placeholder="Enter team city" >
          </label>
          <label>
            <span>Team State:</span>
            <input type="text" v-model="newTeamState" placeholder="Enter team state" >
          </label>
          <button type="submit" class="btn btn-primary" @click="updateTeam">Update Team</button>
          <button type="button" class="btn btn-secondary" @click="cancelForm">Cancel</button>
        </fieldset>
      </form>
    </section>

    <img src="/images/teamlogo.jpg" class="profilepicture"/>

    <section>
      <h2>Coach:</h2>
       <article @click="showCoach(coach.coachId)" class="teamCoach">{{ coach.name }}</article>  
       <h2>Players:</h2>
      <section class="player-list">
        <article  v-for="player in players" :key="player.id" @click="showPlayer(player.playerId)">
          <h3>{{ player.name }}</h3>
        </article>
      </section>
    </section>
    <button @click="$router.go(-1)">Back to Previous</button>
  </article>
</template>

<script>
import teamService from '@/services/TeamService'
import coachService from '@/services/CoachService'
import playerService from '@/services/PlayerService'

export default {
  data(){
    return {
      team: null,
      coach: [],
      players: [],
      showForm: false,
      newTeamName: '',
      newTeamCity: '',
      newTeamState: '',
      newTeamCoach: ''
    };
  },
  created(){
    teamService.getTeam(this.$route.params.id)
      .then(response => {
        this.team = response.data;
      }).catch(error => {
        console.error(error);
      });
      coachService.getCoach(this.$route.params.id)
      .then(response => {
        this.coach = response.data;
      }).catch(error => {
        console.error(error);
      });
      playerService.getPlayersForTeam(this.$route.params.id)
      .then(response => {
        this.players = response.data;
      }).catch(error => {
        console.error(error);
      });
  },
  methods: {
    cancelForm() {
      this.showForm = false;
      this.newTeamName = '';
      this.newTeamCity = '';
      this.newTeamState = '';
      this.newTeamCoach = '';
    },
    updateTeam(){
      const updatedTeam = {
        name: this.newTeamName,
        city: this.newTeamCity,
        state: this.newTeamState,
      };

      teamService.updateTeam(this.$route.params.id, updatedTeam)
        .then(response => {
          this.coach = response.data;
          this.showForm = false;
        }).catch(error => {
          console.error(error);
        });
    },
    deleteTeam(){
      teamService.deleteTeam(this.team.teamId)
        .then(response => {
          console.log(response.data);
          this.$router.push({name: 'Team'});
        }).catch(error => {
          console.error(error);
        });
    },
    showPlayer(id){
      this.$router.push({name: 'PlayerDetail', params: {id: id}});
    },
    showCoach(id){
      this.$router.push({name: 'CoachDetail', params: {id: id}});
    },
  }
}
</script>

<style scoped>
  .team-profile {
    max-width: 800px;
    margin: 40px auto;
    padding: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    border-radius: 10px;
  }

  header {
    background-color: #337ab7;
    color: #fff;
    padding: 10px;
    border-radius: 10px 10px 0 0;
  }

  .profilepicture {
        width: 300px;
        height: 300px;
        object-fit: cover;
        border-radius: 150px;
        display: block;
        margin: 0 auto;
        margin-top: 20px;
        margin-bottom: 10px;
    }

  h1 {
    text-align: center;
    font-size: 24px;
    margin-bottom: 10px;
  }
  
  h2, header, img:hover {
    cursor: default;
  }

  p {
    text-align: center;
    font-size: 18px;
  }

  button{
    background-color: #337ab7;
    color: #fff;
    font-size: small;
    padding: 5px 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  .player-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }
  
  article {
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 10px;
    margin: 10px;
    text-align: center;
  }

  article:hover {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    cursor: pointer;
  }

  article h3 {
    font-size: 18px;
    margin: 10px;
  }

  article p {
    font-size: 16px;
    margin: 10px;
  }

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

    label span {
        display: block;
        margin-bottom: 5px;
    }

    input[type="text"], input[type="number"] {
        padding: 10px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 5px;
        width: 100%;
        margin-bottom: 10px;
    }

    button[type="submit"], button[type="button"] {
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

    .updateButton{
    margin-top: 10px;
    margin-right: 5px;
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

        input[type="text"], input[type="number"] {
            width: 100%;
        }

        .coach-list {
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

        input[type="text"], input[type="number"] {
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

        .coach-list {
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