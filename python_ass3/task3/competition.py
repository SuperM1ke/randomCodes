# Copy and paste your code for competition.py herefrom race import *
from runner import Runner
from custom_errors import CustomValueError, CustomTypeError


class Competition:
    MAX_ROUNDS = 3

    def __get_ordinal(self, n):
        suffixes = {1: 'st', 2: 'nd', 3: 'rd'}
        if 11 <= n % 100 <= 13:
            suffix = 'th'
        else:
            suffix = suffixes.get(n % 10, 'th')
        return f"{n}{suffix}"

    def __init__(self, runners, rounds, distances_short, distances_marathon):
        if not isinstance(runners, list) or not all(isinstance(runner, Runner) for runner in runners):
            raise CustomTypeError("runners must be a list of Runner objects")
        if not isinstance(rounds, int) or not (1 <= rounds <= self.MAX_ROUNDS):
            raise CustomValueError("rounds must be an integer between 1 and MAX_ROUNDS")
        if not isinstance(distances_short, list) or not all(isinstance(d, float) for d in distances_short):
            raise CustomTypeError("distances_short must be a list of floats")
        if not isinstance(distances_marathon, list) or not all(isinstance(d, float) for d in distances_marathon):
            raise CustomTypeError("distances_marathon must be a list of floats")
        if len(distances_short) != rounds or len(distances_marathon) != rounds:
            raise CustomValueError("distances lists must have the same length as rounds")

        self.runners = runners
        self.rounds = rounds
        self.distances_short = distances_short
        self.distances_marathon = distances_marathon

        self.leaderboard = {self.__get_ordinal(i): None for i in range(1, len(self.runners) + 1)}

    def conduct_competition(self):
        for current_round in range(self.rounds):
            short_race = ShortRace(self.distances_short[current_round], runners=self.runners)
            short_result = self.conduct_race(short_race)

            marathon_race = MarathonRace(self.distances_marathon[current_round], runners=self.runners)
            marathon_result = self.conduct_race(marathon_race)

            for runner in self.runners:
                if any(result[1] == "DNF" for result in short_result + marathon_result if result[0] == runner):
                    runner.recover_energy(Runner.max_energy)

            self.update_leaderboard(short_result)
            self.update_leaderboard(marathon_result)

        return self.leaderboard

    def conduct_race(self, race):
        return race.conduct_race()

    def update_leaderboard(self, results):
        points = len(results) - 1
        for runner, time in results:
            if time == "DNF":
                continue
            found = False
            for key, value in self.leaderboard.items():
                if value is None:
                    self.leaderboard[key] = (runner.name, points)
                    found = True
                    break
                elif value[0] == runner.name:
                    self.leaderboard[key] = (runner.name, value[1] + points)
                    found = True
                    break
            if not found:
                for key, value in self.leaderboard.items():
                    if value is None:
                        self.leaderboard[key] = (runner.name, points)
                        break
            points -= 1

        sorted_leaderboard = sorted(
            self.leaderboard.items(),
            key=lambda item: (item[1] is not None, item[1][1] if item[1] is not None else 0),
            reverse=True
        )
        self.leaderboard = {k: v for k, v in sorted_leaderboard}

    def print_leaderboard(self):
        print("Leaderboard\n")
        for key, value in self.leaderboard.items():
            if value:
                print(f"{key} - {value[0]} ({value[1]})")
            else:
                print(f"{key} - None")


if __name__ == '__main__':
    runners = [
        Runner("Elijah", 19, 'Australia', 6.4, 5.2),
        Runner("Rupert", 67, 'Botswana', 2.2, 1.8),
        Runner("Phoebe", 12, 'France', 3.4, 2.8),
        Runner("Lauren", 13, 'Iceland', 4.4, 5.1),
        Runner("Chloe", 21, 'Timor-Leste', 5.2, 1.9)
    ]

    competition = Competition(runners, 3, [0.5, 0.6, 1.2], [4.0, 11.0, 4.5])
    competition.conduct_competition()
    competition.print_leaderboard()

