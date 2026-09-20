# Fuel Calculator Android App for Sim-Racing
Calculate how many laps the race will be and how much fuel you'll need

<img width="360" height="800" alt="Image" src="https://github.com/user-attachments/assets/9427e84f-c43e-46d5-9953-731e667c804c" />

### Input parameters
* Race duration
  * Minutes can be a decimal number
* Lap time
  * Seconds can be a decimal number
  * If you want to be safer, use a slightly quicker laptime than you'd expect
  * For a short race where you don't expect to be lapped, use the class leader's laptime
* Fuel Consumption
  * Estimated fuel consumption per lap
* Safety margin
  * Additional laps to add as a safety margin
  * Defaults to 1

### Output information
* Number of laps:
  * The estimated length of the race based on lap time and race length
  * Includes the safety margin
* Total Fuel Needed:
  * Calculated based on the expected length of the race
  * Automatically rounds up the estimated lap count
    * i.e. if the calculated number of laps is 6.2, it will calculate based on a 7 lap stint