package com.example.ecs;

import com.example.components.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ECS {
    private Map<Class<? extends Component>, Map<Integer, Component>> componentStores = new ConcurrentHashMap<>();
    Map<Integer, List<Script>> scripts = new HashMap<>();

    public <T extends Component> void addComponent(int entityId, T component) {
        if(component instanceof Script){
            scripts.computeIfAbsent(entityId, k -> new ArrayList<>()).add((Script) component);
        }
        else{
            componentStores.computeIfAbsent(component.getClass(), k -> new HashMap<>()).put(entityId, component);
        }

    }
    public List<Script> getScripts(int entityId) {
        return scripts.getOrDefault(entityId, Collections.emptyList());
    }
    public <T extends Component> T getComponent(int entityId, Class<T> compClass) {
        if(Script.class.isAssignableFrom(compClass)){
            return (T) scripts.get(entityId).get(0);
        }

        Map<Integer, Component> store = componentStores.get(compClass);
        if (store == null) return null;
        return compClass.cast(store.get(entityId));
    }

    public <T extends Component> Collection<T> getAllComponentsOfType(Class<T> componentClass) {
        if (Script.class.isAssignableFrom(componentClass)) {
            // Flatten all scripts from all entities into a single list
            List<T> allScripts = new ArrayList<>();
            for (List<Script> scriptList : scripts.values()) {
                for (Script script : scriptList) {
                    // Cast safe because script extends Script which is assignable from componentClass
                    allScripts.add(componentClass.cast(script));
                }
            }
            return allScripts;
        }
        Map<Integer, Component> store = componentStores.get(componentClass);
        if (store == null) {
            return Collections.emptyList();
        }
        // Cast each Component to T and return as a Collection
        Collection<Component> components = store.values();
        // Unsafe cast but safe as we control insertion
        @SuppressWarnings("unchecked")
            Collection<T> typedComponents = (Collection<T>) components;
        return typedComponents;
    }
    @SafeVarargs
    public final Set<Integer> getEntitiesWithComponents(Class<? extends Component>... componentClasses) {
        if (componentClasses.length == 0) {
            return Collections.emptySet();
        }

        // For the first component class, collect entities from all stores whose key is assignable to it
        Set<Integer> result = new HashSet<>();
        Class<? extends Component> firstClass = componentClasses[0];
        boolean foundAny = false;

        for (Class<? extends Component> storedClass : componentStores.keySet()) {
            if (firstClass.isAssignableFrom(storedClass)) {
                Map<Integer, Component> store = componentStores.get(storedClass);
                if (store != null) {
                    result.addAll(store.keySet());
                    foundAny = true;
                }
            }
        }
        if (!foundAny) {
            return Collections.emptySet();
        }

        // For other component classes, intersect similarly
        for (int i = 1; i < componentClasses.length; i++) {
            Class<? extends Component> compClass = componentClasses[i];
            Set<Integer> tempSet = new HashSet<>();
            boolean found = false;

            for (Class<? extends Component> storedClass : componentStores.keySet()) {
                if (compClass.isAssignableFrom(storedClass)) {
                    Map<Integer, Component> store = componentStores.get(storedClass);
                    if (store != null) {
                        tempSet.addAll(store.keySet());
                        found = true;
                    }
                }
            }

            if (!found) {
                return Collections.emptySet();
            }

            result.retainAll(tempSet);
            if (result.isEmpty()) {
                return result;
            }
        }

        return result;
    }

}
