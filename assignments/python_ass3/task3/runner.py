# Copy and paste your code for runner.py here
from custom_errors import *
import csv
from typing import Union

class Runner:
    max_energy: int = 1000 #the max energy is 1000, which should be a whole class variable not a function one

    def __init__(self, name, age, country, sprint_speed, endurance_speed):
        # name must be an alphanumeric string (can contain spaces)
        if not isinstance(name, str) or not name.replace(" ","").isalnum:
            raise CustomTypeError("Name must be an alphanumeric string")
        
        #age must be an integer between 5 and 120 
        if not isinstance(age, int) :
            raise CustomTypeError("age must be an integer")
        if not (5 <= age <= 120):
            raise CustomValueError("age must be an integer between 5 and 120")
        
        #country must be a string and 
        #its value must be from the ones contained in the 'name' 
        #column of the csv countries.csv provided in the directory this file is currently being run,
        if not isinstance(country, str) or not self.validation_country(country):
            raise CustomValueError("country must be a valid string like in countries.csv")
        
        #sprint_speed must be a float and its value should lie between 2.2 and 6.8 in meters per second (both inclusive)
        if not isinstance(sprint_speed, float):
            raise CustomTypeError("sprint_speed must be a float")
        if not (2.2 <= sprint_speed <= 6.8):
            raise CustomValueError("sprint_speed must be a float between 2.2 and 6.8")
        
        #endurance_speed must be a float and its value should lie between 1.8 and 5.4 in meters per second (both inclusive).
        if not isinstance(endurance_speed, float):
            raise CustomTypeError("endurance_speed must be a float")
        if not (1.8 <= endurance_speed <= 5.4):
            raise CustomValueError("endurance_speed must be a float between 1.8 and 5.4 ")
        
        self.name = name
        self.age = age
        self.country = country
        self.sprint_speed = sprint_speed
        self.endurance_speed = endurance_speed
        self.energy = Runner.max_energy

    @staticmethod
    #to validate if the country in the file
    def validation_country(country: str) -> bool:
        with open ('countries.csv',newline = '') as csvfile:
            reader = csv.DictReader(csvfile)
            return any(row['name'] == country for row in reader)

    def drain_energy(self, drain_points: int):
        if not isinstance(drain_points,int):
            raise CustomTypeError("drain_points must be a integer")
        if not (0 <= drain_points <= Runner.max_energy):
            raise CustomValueError("drain_points must be between 0 and max_energy")
        self.energy = max(self.energy - drain_points, 0)

    def recover_energy(self, recovery_amount: int):
        if not isinstance(recovery_amount, int):
            raise CustomTypeError("recover_energy must be a integer")
        if not (0 <= recovery_amount <= Runner.max_energy):
            raise CustomValueError("recover_energy must between 0 to max_energy")
        self.energy = min(self.energy + recovery_amount, Runner.max_energy)

    def run_race(self, race_type: str, distance: float) -> float:
        if not isinstance(race_type, str):
            raise CustomTypeError("race type must be a string")
        if race_type not in ["short", "long"]:
            raise CustomValueError("race type must be either short or long")
        if not isinstance(distance, float):
            raise CustomTypeError("distance must be a float number")
        if distance <= 0:
            raise CustomValueError("distance must be a positive number")

        speed = self.sprint_speed if race_type == "short" else self.endurance_speed
        time_taken = (distance * 1000) / speed
        return round(time_taken, 2)


    def __str__(self):
        return f"Name: {self.name} Age: {self.age} Country: {self.country}"

if __name__ == '__main__':
    runner = Runner('Elijah', 18, 'Australia', 5.8, 4.4)
    
    # running a short race
    time_taken = runner.run_race('short', 2.0)
    print(f"Runner {runner.name} took {time_taken} seconds to run 2km!")
    


