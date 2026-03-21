package org.vrglab.azure.azurelib.common.util.state;

/**
 * Represents a state machine that handles transitions between different states. A state machine maintains a current
 * state and allows transitions to new states, invoking lifecycle methods on each state during transitions.
 *
 * @param <C> the type of the context associated with the state machine, which extends {@link StateMachineContext}
 * @param <T> the type of the states managed by the state machine, which extends {@link State}
 */
public abstract class StateMachine<C extends StateMachineContext, T extends State<C>> {

    private final C _reusableContext;

    private T _state;

    public StateMachine(T initialState) {
        this._state = initialState;
        this._reusableContext = createContext();
    }

    protected abstract C createContext();

    public void update(C context) {
        _state.onUpdate(context);
    }

    public C getContext() {
        return _reusableContext;
    }

    public T getState() {
        return _state;
    }

    public void setState(T newState) {
        _state.onExit(_reusableContext);
        this._state = newState;
        newState.onEnter(_reusableContext);
    }
}
