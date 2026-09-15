using System.Net.WebSockets;
using Culinario.Application.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Culinario.Web.Controllers;

[Authorize]
public class LiveController : Controller
{
    [HttpGet("live/status")]
    public IActionResult Status()
    {
        return Json(new { message = RecipeRealtimeState.LatestEvent });
    }

    [HttpGet("live/ws")]
    public async Task GetWebSocket()
    {
        if (!HttpContext.WebSockets.IsWebSocketRequest)
        {
            HttpContext.Response.StatusCode = StatusCodes.Status400BadRequest;
            return;
        }

        using var socket = await HttpContext.WebSockets.AcceptWebSocketAsync();
        var lastMessage = string.Empty;

        while (socket.State == WebSocketState.Open)
        {
            var current = RecipeRealtimeState.LatestEvent;
            if (!string.Equals(current, lastMessage, StringComparison.Ordinal))
            {
                var payload = System.Text.Encoding.UTF8.GetBytes(current);
                await socket.SendAsync(new ArraySegment<byte>(payload), WebSocketMessageType.Text, true, CancellationToken.None);
                lastMessage = current;
            }

            await Task.Delay(1000);
        }
    }
}
