class StackEmptyException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    StackEmptyException(String err)
    {
        super(err);
    }
}
