package com.example.ecs;

import com.example.Debug;
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
    public void removeAllComponents(int entityId) {
        for (Map<Integer, Component> store : componentStores.values()) {
            store.remove(entityId);
        }
    }
    public void removeAllScripts(int entityId){
        scripts.remove(entityId);
    }
    public List<Script> getScripts(int entityId) {
        return scripts.getOrDefault(entityId, Collections.emptyList());
    }
    public <T extends Component> T getComponent(int entityId, Class<T> compClass) {
        if(Script.class.isAssignableFrom(compClass)){
            if (scripts.get(entityId)!=null)
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
            return Collections.emptySet(); // No components requested
        }

        // Start with the first component type
        Map<Integer, Component> firstStore = componentStores.get(componentClasses[0]);
        if (firstStore == null) return Collections.emptySet();

        // Initialize result with the entity IDs from the first component map
        Set<Integer> result = new HashSet<>(firstStore.keySet());

        // Intersect with the keys from all other component types
        for (int i = 1; i < componentClasses.length; i++) {
            Map<Integer, Component> store = componentStores.get(componentClasses[i]);
            if (store == null) return Collections.emptySet(); // No entities have this component
            result.retainAll(store.keySet());
            if (result.isEmpty()) return result; // Early exit
        }

        return result;
    }


}
