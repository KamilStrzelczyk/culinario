using System.Collections.Concurrent;

namespace Culinario.Application.Services;

public static class RecipeRealtimeState
{
    private static readonly Mutex StateMutex = new();
    private static readonly SemaphoreSlim StateGate = new(1, 1);
    private static readonly ConcurrentDictionary<string, string> Activity = new();

    public static string LatestEvent
    {
        get
        {
            return Activity.GetValueOrDefault("latest", "System ready");
        }
    }

    public static void QueueSignal(string message)
    {
        ThreadPool.QueueUserWorkItem(_ =>
        {
            Update(message).GetAwaiter().GetResult();
        });
    }

    public static async Task Update(string message)
    {
        await StateGate.WaitAsync();
        try
        {
            StateMutex.WaitOne();
            try
            {
                Activity["latest"] = message;
            }
            finally
            {
                StateMutex.ReleaseMutex();
            }
        }
        finally
        {
            StateGate.Release();
        }
    }
}
