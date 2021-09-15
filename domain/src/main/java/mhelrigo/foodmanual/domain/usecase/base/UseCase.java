package mhelrigo.foodmanual.domain.usecase.base;

public abstract class UseCase<T, Params> {
    public UseCase() {
    }

    public abstract T execute(Params parameter);
}
