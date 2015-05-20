public class BaseballElimination {

 private class Team implements Comparable<Team> {

  private final int order;
  private final String name;
  private final int wins;
  private final int losses;
  private final int remaining;

  private Team(int order, String name, int wins, int loses, int remaining) {
   this.order = order;
   this.name = name;
   this.wins = wins;
   this.losses = loses;
   this.remaining = remaining;
  }

  private Team(String name) {
   this.order = -1;
   this.name = name;
   this.wins = -1;
   this.losses = -1;
   this.remaining = -1;
  }

  @Override
  public int compareTo(Team o) {
   return this.name.compareTo(o.name);
  }

 }

 private final SET<Team> teams;
 private final int[][] games;

 public BaseballElimination(String filename) {
  // create a baseball division from given filename in format specified below
  In file = new In(filename);
  int teamsCount = file.readInt();
  games = new int[teamsCount][teamsCount];
  teams = new SET<Team>();
  for (int i = 0; i < teamsCount; i++) {
   Team team = new Team(i, file.readString(), file.readInt(), file.readInt(),
     file.readInt());
   teams.add(team);
   for (int j = 0; j < teamsCount; j++) {
    games[i][j] = file.readInt();
   }
  }
 }

 public int numberOfTeams() {
  // number of teams
  return teams.size();
 }

 public Iterable<String> teams() {
  // all teams
  Bag<String> teamNames = new Bag<String>();
  for (Team team : this.teams) {
   teamNames.add(team.name);
  }
  return teamNames;
 }

 public int wins(String team) {
  // number of wins for given team
  return getTeamByName(team).wins;
 }

 public int losses(String team) {
  // number of losses for given team
  return getTeamByName(team).losses;
 }

 public int remaining(String team) {
  // number of remaining games for given team
  return getTeamByName(team).remaining;
 }

 public int against(String team1, String team2) {
  // number of remaining games between team1 and team2
  return games[getTeamByName(team1).order][getTeamByName(team2).order];
 }

 public boolean isEliminated(String team) {
  // is given team eliminated?
  return certificateOfElimination(team) != null;
 }

 public Iterable<String> certificateOfElimination(String team) {
  // subset R of teams that eliminates given team; null if not eliminated
  Team currentTeam = getTeamByName(team);
  int teamsOffset = ((numberOfTeams() * numberOfTeams()) - numberOfTeams()) / 2 + 1;
  FlowNetwork fn = new FlowNetwork(teamsOffset + numberOfTeams() + 1);
  Bag<String> certificate = new Bag<String>();
  for (Team opponent : teams) {
   if (opponent.order == currentTeam.order) {
    continue;
   }
   if (currentTeam.wins + currentTeam.remaining < opponent.wins) {
    certificate.add(opponent.name);
    continue;
   }
   fn.addEdge(new FlowEdge(teamsOffset + opponent.order, fn.V() - 1, currentTeam.wins + currentTeam.remaining - opponent.wins));
   for (int j = opponent.order + 1; j < numberOfTeams(); j++) {
    if (j == currentTeam.order) {
     continue;
    }
    int gameEdge = opponent.order * numberOfTeams() + j
      - ((((opponent.order + 1) * (opponent.order + 2)) / 2)) + 1;
    fn.addEdge(new FlowEdge(0, gameEdge, games[opponent.order][j]));
    fn.addEdge(new FlowEdge(gameEdge, teamsOffset + opponent.order,
      Double.MAX_VALUE));
    fn.addEdge(new FlowEdge(gameEdge, teamsOffset + j, Double.MAX_VALUE));
   }
  }
  FordFulkerson ff = new FordFulkerson(fn, 0, fn.V() - 1);
  for (Team opponent : teams) {
   if (opponent.order == currentTeam.order) {
    continue;
   }
   if (ff.inCut(opponent.order + teamsOffset)) {
    certificate.add(opponent.name);
   }
  }
  if (certificate.isEmpty()) {
   return null;
  }
  return certificate;
 }

 private Team getTeamByName(String name) {
  Team team = teams.floor(new Team(name));
  if (team.name.equals(name)) {
   return team;
  }
  throw new IllegalArgumentException();
 }

}
