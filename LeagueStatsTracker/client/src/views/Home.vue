<script>
  export default {
  }
</script>

<template>
  <div class="container">
    <header>
      <h1>Minor Basketball Association</h1>
    </header>

    <img src="/images/basketballLeague.jpg" class="LeagueLogo"/>

    <main>
      <p>Welcome to the Minor Basketball Association website!
        <br> 
        Here you can find information about the teams, players, and coaches in the league.
      </p>

      

      <div class="section">
        <h2>Teams</h2>
        <p>Click on teams to see more.</p>
        <router-link to="/team" class="btn">Teams</router-link>
      </div>

      <div class="section">
        <h2>Players</h2>
        <p>Click on players to see more.</p>
        <router-link to="/player" class="btn">Players</router-link>
      </div>

      <div class="section">
        <h2>Coaches</h2>
        <p>Click on coaches to see more.</p>
        <router-link to="/coach" class="btn">Coaches</router-link>
      </div>
    </main>
  </div>
</template>

<style scoped>
  .container {
    max-width: 800px;
    margin: 40px auto;
    padding: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
  }

  .LeagueLogo {
    width: 400px;
    height: 400px;
    border-radius: 350px;
    margin-bottom: 20px;
    object-fit: cover;

  }

  header {
    padding: 10px;
    text-align: center;
  }

  h1 {
    color: black;
    font-size: 24px;
    margin-bottom: 10px;
  }

  main {
    padding: 20px;
  }

  .section {
    margin-bottom: 20px;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }

  h2 {
    font-size: 18px;
    margin-bottom: 10px;
  }

  p {
    font-size: 16px;
    margin-bottom: 20px;
  }

  .btn {
    background-color: #337ab7;
    color: #fff;
    border: none;
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    border-radius: 10px;
  }

  .btn:hover {
    background-color: #23527c;
  }
</style>