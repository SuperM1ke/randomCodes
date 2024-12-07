from competition import Competition
from runner import Runner
from custom_errors import CustomTypeError, CustomValueError


def create_runner(runner_name, runner_age, runner_country, sprint_speed, endurance_speed):
    try:
        name, age, country, sprint_speed, endurance_speed = runner_input.split('/')
        age = int(age)
        sprint_speed = float(sprint_speed)
        endurance_speed = float(endurance_speed)
        return Runner(name, age, country, sprint_speed, endurance_speed)
    except ValueError:
        print(" ERROR: Incorrect number of fields")
    except CustomTypeError as e:
        print(f" ERROR: {e}")
    except CustomValueError as e:
        print(f" ERROR: {e}")

def create_competition(runners, rounds, distances_short, distances_long):
    try:
        rounds, distances_short, distances_marathon = competition_input.split('/')
        rounds = int(rounds)
        distances_short = [float(i) for i in distances_short.split(',')]
        distances_marathon = [float(i) for i in distances_marathon.split(',')]
        return Competition(runners, rounds, distances_short, distances_marathon)
    except ValueError:
        print(" ERROR: Incorrect number of fields.")
    except CustomTypeError as e:
        print(f" ERROR: {e}")
    except CustomValueError as e:
        print(f" ERROR: {e}")


def main():
    # Ask the user to create runners (until they decide to add no more)
    runners = []

    while True:
        runner_input = input("Add runner - name/age/country/sprint speed/marathon speed (blank line stops):")

        if runner_input == "":
            break
        runner = create_runner(runner_input)
        if runner:
            runners.append(runner)

    if not runners:
        print("no runners added")
        return 
    print("Done creating runners!")

    while True:
        competition_input = input("Create competition - rounds/sprint distances/marathon distances:")
        competition = create_competition(runners, competition_input)
        if competition:
            break
    print("Done creating competition!")

    print("Executing the competition!")

    competition.conduct_competition()
    print("Competition concluded!")

    print("Leaderboard")

    competition.print_leaderboard()
       
    

if __name__ == '__main__':
    main()

