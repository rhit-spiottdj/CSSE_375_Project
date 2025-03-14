# Instructor Meeting Week 6

Attendees: Yiji, Brady, Andrew, Christian

Absent: Dom

## Progress evaluation
1. One Single Turn of Game? Left: connecting GUI to the domain. Domain is almost all done. 
2. GUI?
3. Linters:
    - Checkstyle? Found in the build script. But you may not want ignoreFailures = true. Also, would want to augment the checkstyle configuration file.
    - Spotbugs? Found.
    - Jacoco? Found.
    - Pitest? Found.

## Recommendations and Other Notes
1. All the return type, method parameter type, and instance variable type should be changed Map<XXX> instead of HasMap<XXX>.
The reason is we want to program to interface but not implementation as much as possible. HasMap is just an implementation of Map.
For example, your Bank class has a field `resources` of type HashMap<ResourceType, Integer>.
But all we care about is `resources` has all the properties that a Map has, but not necessarily HashMap.
So in the future, if for some reason, LinkedHashMap provides better performance, you will not need to change the implementation of Bank at all.
2. In the code in the main branch, there are variables that are never used. For instance, GameManager class has a field ` private final Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.WHITE};` but `colors` is not used anywhere. Make sure to reexamine your development process and see whether you are following TDD.
3. Consider using TODO for comments like `    //Will need an owner (Player) and location (Edge or Point) associated with the Road as well` (in Road class). Do "// TODO: XXX" instead if you absolute HAVE TO leave that comment there. The best approach is to not leave comments like that. Isntead, use Project Boards, GitHub issues or draft PR to convey that message.
4. The main branch should not contain any commented out code, like your Path class.
5. Consider removing magic numbers (which I am surprised that Checkstyle hasn't reported issues about). See explaination here: https://checkstyle.sourceforge.io/apidocs/com/puppycrawl/tools/checkstyle/checks/coding/MagicNumberCheck.html. But basically, no numeric literal should be used directly but you should first create a constant variable and then use that constant variable. Some of the violations in your code:
```
In Shuffler class, createShuffledResourceTypes method:
        for (int i = 0; i < 4; i++) {
            resourceTypes.add(ResourceType.GRAIN);
            resourceTypes.add(ResourceType.LUMBER);
            resourceTypes.add(ResourceType.WOOL);
        }
        for (int i = 0; i < 3; i++) {
            resourceTypes.add(ResourceType.ORE);
            resourceTypes.add(ResourceType.BRICK);
        }
 What does 4 refer to? Also 3.

Similarly, in the other methods in this class, there are 11, 7, 6, and 8. What do they stand for? Give them a meaning through a constant variable name. 
```
