class QueueEmptyException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    QueueEmptyException(String err)
    {
        super(err);
    }
}
