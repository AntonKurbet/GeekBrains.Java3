package ru.gb.chat.network;

import java.net.ServerSocket;
import java.net.Socket;

public interface ServerSocketThreadListener {
    void onServerStart(ServerSocketThread thread);
    void onServerStop(ServerSocketThread thread);
    void onServerSocketCreated(ServerSocketThread thread, ServerSocket server);
    void onServerTimeout(ServerSocketThread thread, ServerSocket server);
    void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket);
    void onServerException(ServerSocketThread thread, Throwable exception);
}

/*
1) Для чего в методы интерфейсов SSTListener и STListener передается так много параметров? В реализации большинства из них просто вызывается метод putLog.
2) Класс SST, метод run: почему метод listener.onServerStop(this) вызывается в блоке finally? И как он останавливает сервер? в реализации этого метода в классе ChatServer вызывается метод putLog, который просто выводит в консоль сообщение.
3) Класс SocketThread, метод run: почему потоки ввода вывода мы создаем внтури фигурных скобок try? а не передаем параметры в try-with-resoursses?
Уточнение: как SocketThread узнает какой именно слушатель к нему подключился? В конструктор принимается интерфейс SocketThreadListener, который реализовывают 2 класса (ChatServer и ClientGUI). Я правильно поняла, что самому классу SocketThread по большому счёту всё равно на то, какой конкретно класс его слушает, он просто передает в конкретный объект класса-слушателя информацию о том, какой метод необходимо вызвать? Собственно, для этого и используются интерфейсы, вместо классов в конструкторе?

Когда клиент создаёт сокет через new, и сервер создает сокет через accept(), то эти две половинки становятся одним объектом? Или же на каждом создаются свои Input и OuputStream? (для себя пока предположил, что они все-таки разные)
Зачем метод putLog() в потоке?
Сколько раз создается объект ServerSocket? Или же один и тот же объект через accept() создает серверные половинки сокета каждый в своем потоке?
не до конца могу уловить суть взаимоотношений всех модулей нашей программы, её архитектуры. Если можно, коротко пройдитесь по этому вопросу.

1.Почему нет имплементации SocketThreadListener в SocketThread?аналогично ServerSocketListener ServerSocketThread
2.JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE); - эта конструкция показывает в компоненте всплывающие исключения?
3. Нет понимания как сделать стоп клиента по кнопке Disconnect,вообще дз оказалось сложным

Как работает ключевое слово this в классах ServerSocketThread и SocketThread?
Для чего используется метод flush() в классах SocketThread (43 строка) и ClientGUI (118 строка)?
Зачем нужен данный код Thread.setDefaultUncaughtExceptionHandler(this); в ClientGUI (38 строчка) и ServerGUI (30 строчка)?
Можете ли представить общую блок-схему взаимодействия между собой семи классов и интерфейсов, используемых на данный момент?
В каком случае переопределяется метод run(), всегда ли его нужно переопределять при использовании многопоточности? В каком случае класс имплементирует Runnable, а в каком случае наследуется от Thread? В чем разница между ними?
Если мне нужно синхронизировать методы или объекты, класс должен имплементировать Runnable или наследоваться от Thread при этом? Synchronized используется только с потоками?

Можно еще раз про трай с ресурсами в контексте нашего сервера?

interface ServerSocketThreadListener и SocketThreadListener - не понял как interface работает и что его методы выполняют
что выполняет метод uncaughtException в классе ServerGUI

1. С трудом понимается взаимосвязь Листнеров (когда их нужно создавать) и подбор методов в них.
Сложно понимать абстракции.
2. Как обычно логируются штатные исключения? Пример из текущего кода
try {
client = server.accept();
}catch (SocketTimeoutException e){
System.out.println("Ping? Pong!");
3. Моежет чуть подробнее объсянить синтаксис создания сокетов из "полусокетов"
ServerSocket serverSocket = new ServerSocket(8189);
Socket client = serverSocket.accept())
Socket socket = new Socket("127.0.0.1", 8189)

*Не до конца понятны методы описанные в интерфейсе public interface ServerSocketThreadListener;
*Отдельно про TimeOut (Что это? зачем?)
* */