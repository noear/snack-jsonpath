package benchmark.snack4.json;

import benchmark.snack4.json._models.UserGroupModel;
import benchmark.snack4.json._models.UserModel;
import org.junit.jupiter.api.Test;
import org.noear.snack4.Feature;
import org.noear.snack4.ONode;
import org.noear.snack4.Options;

import java.util.*;

public class SpeedSnack4Test {

    /**
     * 测试性能（大json）
     */
    @Test
    public void test00() throws Exception {
        //10000=>2377,2190,2285 //2203,2205,2140 //2540,2680,2318//2234,2179,2213
        //
        //
        //62kb
        //
        String json = "[{\"url\":\"https://api.github.com/gists/8b362386ee0898628409ec63ecdd215b\",\"forks_url\":\"https://api.github.com/gists/8b362386ee0898628409ec63ecdd215b/forks\",\"commits_url\":\"https://api.github.com/gists/8b362386ee0898628409ec63ecdd215b/commits\",\"id\":\"8b362386ee0898628409ec63ecdd215b\",\"node_id\":\"MDQ6R2lzdDhiMzYyMzg2ZWUwODk4NjI4NDA5ZWM2M2VjZGQyMTVi\",\"git_pull_url\":\"https://gist.github.com/8b362386ee0898628409ec63ecdd215b.git\",\"git_push_url\":\"https://gist.github.com/8b362386ee0898628409ec63ecdd215b.git\",\"html_url\":\"https://gist.github.com/8b362386ee0898628409ec63ecdd215b\",\"files\":{\"kafka-cheat-sheet.md\":{\"filename\":\"kafka-cheat-sheet.md\",\"type\":\"text/plain\",\"language\":\"Markdown\",\"raw_url\":\"https://gist.githubusercontent.com/continuum-ajay-sahani/8b362386ee0898628409ec63ecdd215b/raw/23644e1b5f8ec1146dc7eeae0a203f29f486d537/kafka-cheat-sheet.md\",\"size\":2302}},\"public\":true,\"created_at\":\"2019-02-12T07:51:09Z\",\"updated_at\":\"2019-02-12T07:51:09Z\",\"description\":\"Quick command reference for Apache Kafka\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/8b362386ee0898628409ec63ecdd215b/comments\",\"owner\":{\"login\":\"continuum-ajay-sahani\",\"id\":25502831,\"node_id\":\"MDQ6VXNlcjI1NTAyODMx\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/25502831?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/continuum-ajay-sahani\",\"html_url\":\"https://github.com/continuum-ajay-sahani\",\"followers_url\":\"https://api.github.com/users/continuum-ajay-sahani/followers\",\"following_url\":\"https://api.github.com/users/continuum-ajay-sahani/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/continuum-ajay-sahani/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/continuum-ajay-sahani/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/continuum-ajay-sahani/subscriptions\",\"organizations_url\":\"https://api.github.com/users/continuum-ajay-sahani/orgs\",\"repos_url\":\"https://api.github.com/users/continuum-ajay-sahani/repos\",\"events_url\":\"https://api.github.com/users/continuum-ajay-sahani/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/continuum-ajay-sahani/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/3e08c7739e23fe73d6d4f819661135e0\",\"forks_url\":\"https://api.github.com/gists/3e08c7739e23fe73d6d4f819661135e0/forks\",\"commits_url\":\"https://api.github.com/gists/3e08c7739e23fe73d6d4f819661135e0/commits\",\"id\":\"3e08c7739e23fe73d6d4f819661135e0\",\"node_id\":\"MDQ6R2lzdDNlMDhjNzczOWUyM2ZlNzNkNmQ0ZjgxOTY2MTEzNWUw\",\"git_pull_url\":\"https://gist.github.com/3e08c7739e23fe73d6d4f819661135e0.git\",\"git_push_url\":\"https://gist.github.com/3e08c7739e23fe73d6d4f819661135e0.git\",\"html_url\":\"https://gist.github.com/3e08c7739e23fe73d6d4f819661135e0\",\"files\":{\"regular_expression.md\":{\"filename\":\"regular_expression.md\",\"type\":\"text/plain\",\"language\":\"Markdown\",\"raw_url\":\"https://gist.githubusercontent.com/LeeKLTW/3e08c7739e23fe73d6d4f819661135e0/raw/48faef9a244e8da12965c447348ea3028541a848/regular_expression.md\",\"size\":333}},\"public\":true,\"created_at\":\"2019-02-12T07:50:43Z\",\"updated_at\":\"2019-02-12T07:50:57Z\",\"description\":\"#re #regular_expression #re\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/3e08c7739e23fe73d6d4f819661135e0/comments\",\"owner\":{\"login\":\"LeeKLTW\",\"id\":13507805,\"node_id\":\"MDQ6VXNlcjEzNTA3ODA1\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/13507805?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/LeeKLTW\",\"html_url\":\"https://github.com/LeeKLTW\",\"followers_url\":\"https://api.github.com/users/LeeKLTW/followers\",\"following_url\":\"https://api.github.com/users/LeeKLTW/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/LeeKLTW/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/LeeKLTW/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/LeeKLTW/subscriptions\",\"organizations_url\":\"https://api.github.com/users/LeeKLTW/orgs\",\"repos_url\":\"https://api.github.com/users/LeeKLTW/repos\",\"events_url\":\"https://api.github.com/users/LeeKLTW/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/LeeKLTW/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/6eddfdf6643463ea46d1bcb913b3267f\",\"forks_url\":\"https://api.github.com/gists/6eddfdf6643463ea46d1bcb913b3267f/forks\",\"commits_url\":\"https://api.github.com/gists/6eddfdf6643463ea46d1bcb913b3267f/commits\",\"id\":\"6eddfdf6643463ea46d1bcb913b3267f\",\"node_id\":\"MDQ6R2lzdDZlZGRmZGY2NjQzNDYzZWE0NmQxYmNiOTEzYjMyNjdm\",\"git_pull_url\":\"https://gist.github.com/6eddfdf6643463ea46d1bcb913b3267f.git\",\"git_push_url\":\"https://gist.github.com/6eddfdf6643463ea46d1bcb913b3267f.git\",\"html_url\":\"https://gist.github.com/6eddfdf6643463ea46d1bcb913b3267f\",\"files\":{\"gistfile1.txt\":{\"filename\":\"gistfile1.txt\",\"type\":\"text/plain\",\"language\":\"Text\",\"raw_url\":\"https://gist.githubusercontent.com/tarasmorskyi/6eddfdf6643463ea46d1bcb913b3267f/raw/6d0b58c13a78043bfd5ce8e8e72e9bb6aa30b839/gistfile1.txt\",\"size\":694}},\"public\":true,\"created_at\":\"2019-02-12T07:50:26Z\",\"updated_at\":\"2019-02-12T07:50:27Z\",\"description\":\"\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/6eddfdf6643463ea46d1bcb913b3267f/comments\",\"owner\":{\"login\":\"tarasmorskyi\",\"id\":11429342,\"node_id\":\"MDQ6VXNlcjExNDI5MzQy\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/11429342?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/tarasmorskyi\",\"html_url\":\"https://github.com/tarasmorskyi\",\"followers_url\":\"https://api.github.com/users/tarasmorskyi/followers\",\"following_url\":\"https://api.github.com/users/tarasmorskyi/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/tarasmorskyi/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/tarasmorskyi/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/tarasmorskyi/subscriptions\",\"organizations_url\":\"https://api.github.com/users/tarasmorskyi/orgs\",\"repos_url\":\"https://api.github.com/users/tarasmorskyi/repos\",\"events_url\":\"https://api.github.com/users/tarasmorskyi/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/tarasmorskyi/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/0c0470d4a67f4cb360d09f600bc793ed\",\"forks_url\":\"https://api.github.com/gists/0c0470d4a67f4cb360d09f600bc793ed/forks\",\"commits_url\":\"https://api.github.com/gists/0c0470d4a67f4cb360d09f600bc793ed/commits\",\"id\":\"0c0470d4a67f4cb360d09f600bc793ed\",\"node_id\":\"MDQ6R2lzdDBjMDQ3MGQ0YTY3ZjRjYjM2MGQwOWY2MDBiYzc5M2Vk\",\"git_pull_url\":\"https://gist.github.com/0c0470d4a67f4cb360d09f600bc793ed.git\",\"git_push_url\":\"https://gist.github.com/0c0470d4a67f4cb360d09f600bc793ed.git\",\"html_url\":\"https://gist.github.com/0c0470d4a67f4cb360d09f600bc793ed\",\"files\":{\"rolling_mean_nifty_last_year_python_script.py\":{\"filename\":\"rolling_mean_nifty_last_year_python_script.py\",\"type\":\"application/x-python\",\"language\":\"Python\",\"raw_url\":\"https://gist.githubusercontent.com/Pahulpreet86/0c0470d4a67f4cb360d09f600bc793ed/raw/fcc26f5aa461440303a716c623aa7a41918816a5/rolling_mean_nifty_last_year_python_script.py\",\"size\":803}},\"public\":true,\"created_at\":\"2019-02-12T07:50:23Z\",\"updated_at\":\"2019-02-12T07:50:23Z\",\"description\":\"\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/0c0470d4a67f4cb360d09f600bc793ed/comments\",\"owner\":{\"login\":\"Pahulpreet86\",\"id\":24950663,\"node_id\":\"MDQ6VXNlcjI0OTUwNjYz\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/24950663?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/Pahulpreet86\",\"html_url\":\"https://github.com/Pahulpreet86\",\"followers_url\":\"https://api.github.com/users/Pahulpreet86/followers\",\"following_url\":\"https://api.github.com/users/Pahulpreet86/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/Pahulpreet86/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/Pahulpreet86/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/Pahulpreet86/subscriptions\",\"organizations_url\":\"https://api.github.com/users/Pahulpreet86/orgs\",\"repos_url\":\"https://api.github.com/users/Pahulpreet86/repos\",\"events_url\":\"https://api.github.com/users/Pahulpreet86/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/Pahulpreet86/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/8e8868dc6a8b75fad6670e521ed6e921\",\"forks_url\":\"https://api.github.com/gists/8e8868dc6a8b75fad6670e521ed6e921/forks\",\"commits_url\":\"https://api.github.com/gists/8e8868dc6a8b75fad6670e521ed6e921/commits\",\"id\":\"8e8868dc6a8b75fad6670e521ed6e921\",\"node_id\":\"MDQ6R2lzdDhlODg2OGRjNmE4Yjc1ZmFkNjY3MGU1MjFlZDZlOTIx\",\"git_pull_url\":\"https://gist.github.com/8e8868dc6a8b75fad6670e521ed6e921.git\",\"git_push_url\":\"https://gist.github.com/8e8868dc6a8b75fad6670e521ed6e921.git\",\"html_url\":\"https://gist.github.com/8e8868dc6a8b75fad6670e521ed6e921\",\"files\":{\"AfricasTalkingGateway.php\":{\"filename\":\"AfricasTalkingGateway.php\",\"type\":\"application/x-httpd-php\",\"language\":\"PHP\",\"raw_url\":\"https://gist.githubusercontent.com/DavidNgugi/8e8868dc6a8b75fad6670e521ed6e921/raw/27693b065b8846934c6e7b7d5847cba31f750056/AfricasTalkingGateway.php\",\"size\":16439}},\"public\":true,\"created_at\":\"2019-02-12T07:50:19Z\",\"updated_at\":\"2019-02-12T07:50:19Z\",\"description\":\"AfricasTalking Gateway API\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/8e8868dc6a8b75fad6670e521ed6e921/comments\",\"owner\":{\"login\":\"DavidNgugi\",\"id\":5968269,\"node_id\":\"MDQ6VXNlcjU5NjgyNjk=\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/5968269?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/DavidNgugi\",\"html_url\":\"https://github.com/DavidNgugi\",\"followers_url\":\"https://api.github.com/users/DavidNgugi/followers\",\"following_url\":\"https://api.github.com/users/DavidNgugi/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/DavidNgugi/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/DavidNgugi/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/DavidNgugi/subscriptions\",\"organizations_url\":\"https://api.github.com/users/DavidNgugi/orgs\",\"repos_url\":\"https://api.github.com/users/DavidNgugi/repos\",\"events_url\":\"https://api.github.com/users/DavidNgugi/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/DavidNgugi/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/3c1fb73f159e7aa29339ebd3b8e2ff8e\",\"forks_url\":\"https://api.github.com/gists/3c1fb73f159e7aa29339ebd3b8e2ff8e/forks\",\"commits_url\":\"https://api.github.com/gists/3c1fb73f159e7aa29339ebd3b8e2ff8e/commits\",\"id\":\"3c1fb73f159e7aa29339ebd3b8e2ff8e\",\"node_id\":\"MDQ6R2lzdDNjMWZiNzNmMTU5ZTdhYTI5MzM5ZWJkM2I4ZTJmZjhl\",\"git_pull_url\":\"https://gist.github.com/3c1fb73f159e7aa29339ebd3b8e2ff8e.git\",\"git_push_url\":\"https://gist.github.com/3c1fb73f159e7aa29339ebd3b8e2ff8e.git\",\"html_url\":\"https://gist.github.com/3c1fb73f159e7aa29339ebd3b8e2ff8e\",\"files\":{\"playground.rs\":{\"filename\":\"playground.rs\",\"type\":\"text/plain\",\"language\":\"Rust\",\"raw_url\":\"https://gist.githubusercontent.com/rust-play/3c1fb73f159e7aa29339ebd3b8e2ff8e/raw/9684b2442bb908e43b51af838fc46e68eecd3ae8/playground.rs\",\"size\":147}},\"public\":true,\"created_at\":\"2019-02-12T07:49:54Z\",\"updated_at\":\"2019-02-12T07:49:54Z\",\"description\":\"Code shared from the Rust Playground\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/3c1fb73f159e7aa29339ebd3b8e2ff8e/comments\",\"owner\":{\"login\":\"rust-play\",\"id\":37046162,\"node_id\":\"MDQ6VXNlcjM3MDQ2MTYy\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/37046162?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/rust-play\",\"html_url\":\"https://github.com/rust-play\",\"followers_url\":\"https://api.github.com/users/rust-play/followers\",\"following_url\":\"https://api.github.com/users/rust-play/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/rust-play/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/rust-play/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/rust-play/subscriptions\",\"organizations_url\":\"https://api.github.com/users/rust-play/orgs\",\"repos_url\":\"https://api.github.com/users/rust-play/repos\",\"events_url\":\"https://api.github.com/users/rust-play/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/rust-play/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/4b3f12fe7ea853b0d35c45d9250c349e\",\"forks_url\":\"https://api.github.com/gists/4b3f12fe7ea853b0d35c45d9250c349e/forks\",\"commits_url\":\"https://api.github.com/gists/4b3f12fe7ea853b0d35c45d9250c349e/commits\",\"id\":\"4b3f12fe7ea853b0d35c45d9250c349e\",\"node_id\":\"MDQ6R2lzdDRiM2YxMmZlN2VhODUzYjBkMzVjNDVkOTI1MGMzNDll\",\"git_pull_url\":\"https://gist.github.com/4b3f12fe7ea853b0d35c45d9250c349e.git\",\"git_push_url\":\"https://gist.github.com/4b3f12fe7ea853b0d35c45d9250c349e.git\",\"html_url\":\"https://gist.github.com/4b3f12fe7ea853b0d35c45d9250c349e\",\"files\":{\"Startup.cs\":{\"filename\":\"Startup.cs\",\"type\":\"text/plain\",\"language\":\"C#\",\"raw_url\":\"https://gist.githubusercontent.com/mikebrind/4b3f12fe7ea853b0d35c45d9250c349e/raw/272465724c55560965714ddf546da9aa5e8aadc7/Startup.cs\",\"size\":221}},\"public\":true,\"created_at\":\"2019-02-12T07:49:42Z\",\"updated_at\":\"2019-02-12T07:49:42Z\",\"description\":\"\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/4b3f12fe7ea853b0d35c45d9250c349e/comments\",\"owner\":{\"login\":\"mikebrind\",\"id\":648474,\"node_id\":\"MDQ6VXNlcjY0ODQ3NA==\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/648474?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/mikebrind\",\"html_url\":\"https://github.com/mikebrind\",\"followers_url\":\"https://api.github.com/users/mikebrind/followers\",\"following_url\":\"https://api.github.com/users/mikebrind/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/mikebrind/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/mikebrind/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/mikebrind/subscriptions\",\"organizations_url\":\"https://api.github.com/users/mikebrind/orgs\",\"repos_url\":\"https://api.github.com/users/mikebrind/repos\",\"events_url\":\"https://api.github.com/users/mikebrind/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/mikebrind/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/08c9843150741385165f89886149f50b\",\"forks_url\":\"https://api.github.com/gists/08c9843150741385165f89886149f50b/forks\",\"commits_url\":\"https://api.github.com/gists/08c9843150741385165f89886149f50b/commits\",\"id\":\"08c9843150741385165f89886149f50b\",\"node_id\":\"MDQ6R2lzdDA4Yzk4NDMxNTA3NDEzODUxNjVmODk4ODYxNDlmNTBi\",\"git_pull_url\":\"https://gist.github.com/08c9843150741385165f89886149f50b.git\",\"git_push_url\":\"https://gist.github.com/08c9843150741385165f89886149f50b.git\",\"html_url\":\"https://gist.github.com/08c9843150741385165f89886149f50b\",\"files\":{\"盒子的定位\":{\"filename\":\"盒子的定位\",\"type\":\"text/plain\",\"language\":null,\"raw_url\":\"https://gist.githubusercontent.com/Smalladragon/08c9843150741385165f89886149f50b/raw/89d76cef9d192d93b2feda4d90d9a848192d8145/%E7%9B%92%E5%AD%90%E7%9A%84%E5%AE%9A%E4%BD%8D\",\"size\":1901}},\"public\":true,\"created_at\":\"2019-02-12T07:49:41Z\",\"updated_at\":\"2019-02-12T07:49:41Z\",\"description\":\"18\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/08c9843150741385165f89886149f50b/comments\",\"owner\":{\"login\":\"Smalladragon\",\"id\":46671120,\"node_id\":\"MDQ6VXNlcjQ2NjcxMTIw\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/46671120?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/Smalladragon\",\"html_url\":\"https://github.com/Smalladragon\",\"followers_url\":\"https://api.github.com/users/Smalladragon/followers\",\"following_url\":\"https://api.github.com/users/Smalladragon/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/Smalladragon/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/Smalladragon/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/Smalladragon/subscriptions\",\"organizations_url\":\"https://api.github.com/users/Smalladragon/orgs\",\"repos_url\":\"https://api.github.com/users/Smalladragon/repos\",\"events_url\":\"https://api.github.com/users/Smalladragon/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/Smalladragon/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/9fa33c5af2ef6692749686e1b3d5a78c\",\"forks_url\":\"https://api.github.com/gists/9fa33c5af2ef6692749686e1b3d5a78c/forks\",\"commits_url\":\"https://api.github.com/gists/9fa33c5af2ef6692749686e1b3d5a78c/commits\",\"id\":\"9fa33c5af2ef6692749686e1b3d5a78c\",\"node_id\":\"MDQ6R2lzdDlmYTMzYzVhZjJlZjY2OTI3NDk2ODZlMWIzZDVhNzhj\",\"git_pull_url\":\"https://gist.github.com/9fa33c5af2ef6692749686e1b3d5a78c.git\",\"git_push_url\":\"https://gist.github.com/9fa33c5af2ef6692749686e1b3d5a78c.git\",\"html_url\":\"https://gist.github.com/9fa33c5af2ef6692749686e1b3d5a78c\",\"files\":{\"item.json\":{\"filename\":\"item.json\",\"type\":\"application/json\",\"language\":\"JSON\",\"raw_url\":\"https://gist.githubusercontent.com/aswin-sw/9fa33c5af2ef6692749686e1b3d5a78c/raw/0cca4995b20f2415137d3b3b0efe948b3d6ad7e1/item.json\",\"size\":2889}},\"public\":true,\"created_at\":\"2019-02-12T07:49:29Z\",\"updated_at\":\"2019-02-12T07:49:29Z\",\"description\":\"\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/9fa33c5af2ef6692749686e1b3d5a78c/comments\",\"owner\":{\"login\":\"aswin-sw\",\"id\":40427381,\"node_id\":\"MDQ6VXNlcjQwNDI3Mzgx\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/40427381?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/aswin-sw\",\"html_url\":\"https://github.com/aswin-sw\",\"followers_url\":\"https://api.github.com/users/aswin-sw/followers\",\"following_url\":\"https://api.github.com/users/aswin-sw/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/aswin-sw/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/aswin-sw/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/aswin-sw/subscriptions\",\"organizations_url\":\"https://api.github.com/users/aswin-sw/orgs\",\"repos_url\":\"https://api.github.com/users/aswin-sw/repos\",\"events_url\":\"https://api.github.com/users/aswin-sw/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/aswin-sw/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/7e3bd0808172fc8e8f0a582e9078580f\",\"forks_url\":\"https://api.github.com/gists/7e3bd0808172fc8e8f0a582e9078580f/forks\",\"commits_url\":\"https://api.github.com/gists/7e3bd0808172fc8e8f0a582e9078580f/commits\",\"id\":\"7e3bd0808172fc8e8f0a582e9078580f\",\"node_id\":\"MDQ6R2lzdDdlM2JkMDgwODE3MmZjOGU4ZjBhNTgyZTkwNzg1ODBm\",\"git_pull_url\":\"https://gist.github.com/7e3bd0808172fc8e8f0a582e9078580f.git\",\"git_push_url\":\"https://gist.github.com/7e3bd0808172fc8e8f0a582e9078580f.git\",\"html_url\":\"https://gist.github.com/7e3bd0808172fc8e8f0a582e9078580f\",\"files\":{\"main.js\":{\"filename\":\"main.js\",\"type\":\"application/javascript\",\"language\":\"JavaScript\",\"raw_url\":\"https://gist.githubusercontent.com/bhaidar/7e3bd0808172fc8e8f0a582e9078580f/raw/4eb342bb8e4653a342ed2d20e59d1b827032b1d0/main.js\",\"size\":1602}},\"public\":true,\"created_at\":\"2019-02-12T07:49:28Z\",\"updated_at\":\"2019-02-12T07:49:28Z\",\"description\":\"todoapp\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/7e3bd0808172fc8e8f0a582e9078580f/comments\",\"owner\":{\"login\":\"bhaidar\",\"id\":1163421,\"node_id\":\"MDQ6VXNlcjExNjM0MjE=\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/1163421?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/bhaidar\",\"html_url\":\"https://github.com/bhaidar\",\"followers_url\":\"https://api.github.com/users/bhaidar/followers\",\"following_url\":\"https://api.github.com/users/bhaidar/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/bhaidar/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/bhaidar/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/bhaidar/subscriptions\",\"organizations_url\":\"https://api.github.com/users/bhaidar/orgs\",\"repos_url\":\"https://api.github.com/users/bhaidar/repos\",\"events_url\":\"https://api.github.com/users/bhaidar/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/bhaidar/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/eafeef6711fa8c7d097fd2a093433950\",\"forks_url\":\"https://api.github.com/gists/eafeef6711fa8c7d097fd2a093433950/forks\",\"commits_url\":\"https://api.github.com/gists/eafeef6711fa8c7d097fd2a093433950/commits\",\"id\":\"eafeef6711fa8c7d097fd2a093433950\",\"node_id\":\"MDQ6R2lzdGVhZmVlZjY3MTFmYThjN2QwOTdmZDJhMDkzNDMzOTUw\",\"git_pull_url\":\"https://gist.github.com/eafeef6711fa8c7d097fd2a093433950.git\",\"git_push_url\":\"https://gist.github.com/eafeef6711fa8c7d097fd2a093433950.git\",\"html_url\":\"https://gist.github.com/eafeef6711fa8c7d097fd2a093433950\",\"files\":{\"gistfile1.txt\":{\"filename\":\"gistfile1.txt\",\"type\":\"text/plain\",\"language\":\"Text\",\"raw_url\":\"https://gist.githubusercontent.com/tarasmorskyi/eafeef6711fa8c7d097fd2a093433950/raw/ffadc72aa151b11760c25cdf097229ce84a1dde3/gistfile1.txt\",\"size\":102}},\"public\":true,\"created_at\":\"2019-02-12T07:49:19Z\",\"updated_at\":\"2019-02-12T07:49:19Z\",\"description\":\"\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/eafeef6711fa8c7d097fd2a093433950/comments\",\"owner\":{\"login\":\"tarasmorskyi\",\"id\":11429342,\"node_id\":\"MDQ6VXNlcjExNDI5MzQy\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/11429342?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/tarasmorskyi\",\"html_url\":\"https://github.com/tarasmorskyi\",\"followers_url\":\"https://api.github.com/users/tarasmorskyi/followers\",\"following_url\":\"https://api.github.com/users/tarasmorskyi/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/tarasmorskyi/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/tarasmorskyi/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/tarasmorskyi/subscriptions\",\"organizations_url\":\"https://api.github.com/users/tarasmorskyi/orgs\",\"repos_url\":\"https://api.github.com/users/tarasmorskyi/repos\",\"events_url\":\"https://api.github.com/users/tarasmorskyi/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/tarasmorskyi/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/d4ae22df3a2cf1f6926d10126e9cb0e4\",\"forks_url\":\"https://api.github.com/gists/d4ae22df3a2cf1f6926d10126e9cb0e4/forks\",\"commits_url\":\"https://api.github.com/gists/d4ae22df3a2cf1f6926d10126e9cb0e4/commits\",\"id\":\"d4ae22df3a2cf1f6926d10126e9cb0e4\",\"node_id\":\"MDQ6R2lzdGQ0YWUyMmRmM2EyY2YxZjY5MjZkMTAxMjZlOWNiMGU0\",\"git_pull_url\":\"https://gist.github.com/d4ae22df3a2cf1f6926d10126e9cb0e4.git\",\"git_push_url\":\"https://gist.github.com/d4ae22df3a2cf1f6926d10126e9cb0e4.git\",\"html_url\":\"https://gist.github.com/d4ae22df3a2cf1f6926d10126e9cb0e4\",\"files\":{\"Recursive files to CSV\":{\"filename\":\"Recursive files to CSV\",\"type\":\"text/plain\",\"language\":null,\"raw_url\":\"https://gist.githubusercontent.com/c5inco/d4ae22df3a2cf1f6926d10126e9cb0e4/raw/007dd94309a9b4e3fa4a65a948511f51e01edbc6/Recursive%20files%20to%20CSV\",\"size\":37}},\"public\":true,\"created_at\":\"2019-02-12T07:49:04Z\",\"updated_at\":\"2019-02-12T07:49:04Z\",\"description\":\"\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/d4ae22df3a2cf1f6926d10126e9cb0e4/comments\",\"owner\":{\"login\":\"c5inco\",\"id\":1253402,\"node_id\":\"MDQ6VXNlcjEyNTM0MDI=\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/1253402?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/c5inco\",\"html_url\":\"https://github.com/c5inco\",\"followers_url\":\"https://api.github.com/users/c5inco/followers\",\"following_url\":\"https://api.github.com/users/c5inco/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/c5inco/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/c5inco/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/c5inco/subscriptions\",\"organizations_url\":\"https://api.github.com/users/c5inco/orgs\",\"repos_url\":\"https://api.github.com/users/c5inco/repos\",\"events_url\":\"https://api.github.com/users/c5inco/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/c5inco/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/d9ec75a6358ab27955755748be99a9d3\",\"forks_url\":\"https://api.github.com/gists/d9ec75a6358ab27955755748be99a9d3/forks\",\"commits_url\":\"https://api.github.com/gists/d9ec75a6358ab27955755748be99a9d3/commits\",\"id\":\"d9ec75a6358ab27955755748be99a9d3\",\"node_id\":\"MDQ6R2lzdGQ5ZWM3NWE2MzU4YWIyNzk1NTc1NTc0OGJlOTlhOWQz\",\"git_pull_url\":\"https://gist.github.com/d9ec75a6358ab27955755748be99a9d3.git\",\"git_push_url\":\"https://gist.github.com/d9ec75a6358ab27955755748be99a9d3.git\",\"html_url\":\"https://gist.github.com/d9ec75a6358ab27955755748be99a9d3\",\"files\":{\"account_linking.json\":{\"filename\":\"account_linking.json\",\"type\":\"application/json\",\"language\":\"JSON\",\"raw_url\":\"https://gist.githubusercontent.com/jaybalanay/d9ec75a6358ab27955755748be99a9d3/raw/132823d2babae9205db11cddc832a557a13e2cd2/account_linking.json\",\"size\":304}},\"public\":true,\"created_at\":\"2019-02-12T07:48:25Z\",\"updated_at\":\"2019-02-12T07:50:04Z\",\"description\":\"account_link\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/d9ec75a6358ab27955755748be99a9d3/comments\",\"owner\":{\"login\":\"jaybalanay\",\"id\":52903,\"node_id\":\"MDQ6VXNlcjUyOTAz\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/52903?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/jaybalanay\",\"html_url\":\"https://github.com/jaybalanay\",\"followers_url\":\"https://api.github.com/users/jaybalanay/followers\",\"following_url\":\"https://api.github.com/users/jaybalanay/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/jaybalanay/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/jaybalanay/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/jaybalanay/subscriptions\",\"organizations_url\":\"https://api.github.com/users/jaybalanay/orgs\",\"repos_url\":\"https://api.github.com/users/jaybalanay/repos\",\"events_url\":\"https://api.github.com/users/jaybalanay/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/jaybalanay/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/5726b72df0d3c69a46c74ee56bc417d1\",\"forks_url\":\"https://api.github.com/gists/5726b72df0d3c69a46c74ee56bc417d1/forks\",\"commits_url\":\"https://api.github.com/gists/5726b72df0d3c69a46c74ee56bc417d1/commits\",\"id\":\"5726b72df0d3c69a46c74ee56bc417d1\",\"node_id\":\"MDQ6R2lzdDU3MjZiNzJkZjBkM2M2OWE0NmM3NGVlNTZiYzQxN2Qx\",\"git_pull_url\":\"https://gist.github.com/5726b72df0d3c69a46c74ee56bc417d1.git\",\"git_push_url\":\"https://gist.github.com/5726b72df0d3c69a46c74ee56bc417d1.git\",\"html_url\":\"https://gist.github.com/5726b72df0d3c69a46c74ee56bc417d1\",\"files\":{\"ResponseTrait.php\":{\"filename\":\"ResponseTrait.php\",\"type\":\"application/x-httpd-php\",\"language\":\"PHP\",\"raw_url\":\"https://gist.githubusercontent.com/DavidNgugi/5726b72df0d3c69a46c74ee56bc417d1/raw/c203e3edd70c7104a131b336cd25492e999093d2/ResponseTrait.php\",\"size\":1107}},\"public\":true,\"created_at\":\"2019-02-12T07:48:14Z\",\"updated_at\":\"2019-02-12T07:48:15Z\",\"description\":\"Response Trait for both success and error responses\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/5726b72df0d3c69a46c74ee56bc417d1/comments\",\"owner\":{\"login\":\"DavidNgugi\",\"id\":5968269,\"node_id\":\"MDQ6VXNlcjU5NjgyNjk=\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/5968269?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/DavidNgugi\",\"html_url\":\"https://github.com/DavidNgugi\",\"followers_url\":\"https://api.github.com/users/DavidNgugi/followers\",\"following_url\":\"https://api.github.com/users/DavidNgugi/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/DavidNgugi/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/DavidNgugi/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/DavidNgugi/subscriptions\",\"organizations_url\":\"https://api.github.com/users/DavidNgugi/orgs\",\"repos_url\":\"https://api.github.com/users/DavidNgugi/repos\",\"events_url\":\"https://api.github.com/users/DavidNgugi/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/DavidNgugi/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/b66cd566262abb9977d1d985cb8b3e2d\",\"forks_url\":\"https://api.github.com/gists/b66cd566262abb9977d1d985cb8b3e2d/forks\",\"commits_url\":\"https://api.github.com/gists/b66cd566262abb9977d1d985cb8b3e2d/commits\",\"id\":\"b66cd566262abb9977d1d985cb8b3e2d\",\"node_id\":\"MDQ6R2lzdGI2NmNkNTY2MjYyYWJiOTk3N2QxZDk4NWNiOGIzZTJk\",\"git_pull_url\":\"https://gist.github.com/b66cd566262abb9977d1d985cb8b3e2d.git\",\"git_push_url\":\"https://gist.github.com/b66cd566262abb9977d1d985cb8b3e2d.git\",\"html_url\":\"https://gist.github.com/b66cd566262abb9977d1d985cb8b3e2d\",\"files\":{\"gistfile1.txt\":{\"filename\":\"gistfile1.txt\",\"type\":\"text/plain\",\"language\":\"Text\",\"raw_url\":\"https://gist.githubusercontent.com/tarasmorskyi/b66cd566262abb9977d1d985cb8b3e2d/raw/7d63b3fa9c8fc877d71e0e1d1663c22ea260f845/gistfile1.txt\",\"size\":207}},\"public\":true,\"created_at\":\"2019-02-12T07:48:12Z\",\"updated_at\":\"2019-02-12T07:48:12Z\",\"description\":\"\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/b66cd566262abb9977d1d985cb8b3e2d/comments\",\"owner\":{\"login\":\"tarasmorskyi\",\"id\":11429342,\"node_id\":\"MDQ6VXNlcjExNDI5MzQy\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/11429342?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/tarasmorskyi\",\"html_url\":\"https://github.com/tarasmorskyi\",\"followers_url\":\"https://api.github.com/users/tarasmorskyi/followers\",\"following_url\":\"https://api.github.com/users/tarasmorskyi/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/tarasmorskyi/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/tarasmorskyi/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/tarasmorskyi/subscriptions\",\"organizations_url\":\"https://api.github.com/users/tarasmorskyi/orgs\",\"repos_url\":\"https://api.github.com/users/tarasmorskyi/repos\",\"events_url\":\"https://api.github.com/users/tarasmorskyi/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/tarasmorskyi/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/880e1314a08bb0af577cc5c807b491c5\",\"forks_url\":\"https://api.github.com/gists/880e1314a08bb0af577cc5c807b491c5/forks\",\"commits_url\":\"https://api.github.com/gists/880e1314a08bb0af577cc5c807b491c5/commits\",\"id\":\"880e1314a08bb0af577cc5c807b491c5\",\"node_id\":\"MDQ6R2lzdDg4MGUxMzE0YTA4YmIwYWY1NzdjYzVjODA3YjQ5MWM1\",\"git_pull_url\":\"https://gist.github.com/880e1314a08bb0af577cc5c807b491c5.git\",\"git_push_url\":\"https://gist.github.com/880e1314a08bb0af577cc5c807b491c5.git\",\"html_url\":\"https://gist.github.com/880e1314a08bb0af577cc5c807b491c5\",\"files\":{\"Boolean.sol\":{\"filename\":\"Boolean.sol\",\"type\":\"text/plain\",\"language\":null,\"raw_url\":\"https://gist.githubusercontent.com/Rbchi/880e1314a08bb0af577cc5c807b491c5/raw/2d45093b9f935e5a9a6def77c2ab6d1c69ca4ba9/Boolean.sol\",\"size\":601}},\"public\":true,\"created_at\":\"2019-02-12T07:48:08Z\",\"updated_at\":\"2019-02-12T07:48:08Z\",\"description\":\"關於solidity -->value type -->bool\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/880e1314a08bb0af577cc5c807b491c5/comments\",\"owner\":{\"login\":\"Rbchi\",\"id\":30424358,\"node_id\":\"MDQ6VXNlcjMwNDI0MzU4\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/30424358?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/Rbchi\",\"html_url\":\"https://github.com/Rbchi\",\"followers_url\":\"https://api.github.com/users/Rbchi/followers\",\"following_url\":\"https://api.github.com/users/Rbchi/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/Rbchi/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/Rbchi/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/Rbchi/subscriptions\",\"organizations_url\":\"https://api.github.com/users/Rbchi/orgs\",\"repos_url\":\"https://api.github.com/users/Rbchi/repos\",\"events_url\":\"https://api.github.com/users/Rbchi/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/Rbchi/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/38d2f3659508d1a8f47997093deeb4c9\",\"forks_url\":\"https://api.github.com/gists/38d2f3659508d1a8f47997093deeb4c9/forks\",\"commits_url\":\"https://api.github.com/gists/38d2f3659508d1a8f47997093deeb4c9/commits\",\"id\":\"38d2f3659508d1a8f47997093deeb4c9\",\"node_id\":\"MDQ6R2lzdDM4ZDJmMzY1OTUwOGQxYThmNDc5OTcwOTNkZWViNGM5\",\"git_pull_url\":\"https://gist.github.com/38d2f3659508d1a8f47997093deeb4c9.git\",\"git_push_url\":\"https://gist.github.com/38d2f3659508d1a8f47997093deeb4c9.git\",\"html_url\":\"https://gist.github.com/38d2f3659508d1a8f47997093deeb4c9\",\"files\":{\"verb20.js\":{\"filename\":\"verb20.js\",\"type\":\"application/javascript\",\"language\":\"JavaScript\",\"raw_url\":\"https://gist.githubusercontent.com/torleifg/38d2f3659508d1a8f47997093deeb4c9/raw/27774d28adc87d1d85c043442614af5360e0e35e/verb20.js\",\"size\":1844}},\"public\":true,\"created_at\":\"2019-02-12T07:48:06Z\",\"updated_at\":\"2019-02-12T07:48:17Z\",\"description\":\"\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/38d2f3659508d1a8f47997093deeb4c9/comments\",\"owner\":{\"login\":\"torleifg\",\"id\":3832517,\"node_id\":\"MDQ6VXNlcjM4MzI1MTc=\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/3832517?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/torleifg\",\"html_url\":\"https://github.com/torleifg\",\"followers_url\":\"https://api.github.com/users/torleifg/followers\",\"following_url\":\"https://api.github.com/users/torleifg/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/torleifg/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/torleifg/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/torleifg/subscriptions\",\"organizations_url\":\"https://api.github.com/users/torleifg/orgs\",\"repos_url\":\"https://api.github.com/users/torleifg/repos\",\"events_url\":\"https://api.github.com/users/torleifg/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/torleifg/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/a6949d1d6f07db68cffc1184884d142e\",\"forks_url\":\"https://api.github.com/gists/a6949d1d6f07db68cffc1184884d142e/forks\",\"commits_url\":\"https://api.github.com/gists/a6949d1d6f07db68cffc1184884d142e/commits\",\"id\":\"a6949d1d6f07db68cffc1184884d142e\",\"node_id\":\"MDQ6R2lzdGE2OTQ5ZDFkNmYwN2RiNjhjZmZjMTE4NDg4NGQxNDJl\",\"git_pull_url\":\"https://gist.github.com/a6949d1d6f07db68cffc1184884d142e.git\",\"git_push_url\":\"https://gist.github.com/a6949d1d6f07db68cffc1184884d142e.git\",\"html_url\":\"https://gist.github.com/a6949d1d6f07db68cffc1184884d142e\",\"files\":{\"vscode.mac.keybindings.json\":{\"filename\":\"vscode.mac.keybindings.json\",\"type\":\"application/json\",\"language\":\"JSON\",\"raw_url\":\"https://gist.githubusercontent.com/jiahut/a6949d1d6f07db68cffc1184884d142e/raw/bd5b424e45723abca08607699d92cdd1c9d3df79/vscode.mac.keybindings.json\",\"size\":655}},\"public\":true,\"created_at\":\"2019-02-12T07:47:57Z\",\"updated_at\":\"2019-02-12T07:47:58Z\",\"description\":\"vscode mac keymapping \",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/a6949d1d6f07db68cffc1184884d142e/comments\",\"owner\":{\"login\":\"jiahut\",\"id\":1067691,\"node_id\":\"MDQ6VXNlcjEwNjc2OTE=\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/1067691?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/jiahut\",\"html_url\":\"https://github.com/jiahut\",\"followers_url\":\"https://api.github.com/users/jiahut/followers\",\"following_url\":\"https://api.github.com/users/jiahut/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/jiahut/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/jiahut/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/jiahut/subscriptions\",\"organizations_url\":\"https://api.github.com/users/jiahut/orgs\",\"repos_url\":\"https://api.github.com/users/jiahut/repos\",\"events_url\":\"https://api.github.com/users/jiahut/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/jiahut/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/11d35446ecab96c58e8fd14173575eb4\",\"forks_url\":\"https://api.github.com/gists/11d35446ecab96c58e8fd14173575eb4/forks\",\"commits_url\":\"https://api.github.com/gists/11d35446ecab96c58e8fd14173575eb4/commits\",\"id\":\"11d35446ecab96c58e8fd14173575eb4\",\"node_id\":\"MDQ6R2lzdDExZDM1NDQ2ZWNhYjk2YzU4ZThmZDE0MTczNTc1ZWI0\",\"git_pull_url\":\"https://gist.github.com/11d35446ecab96c58e8fd14173575eb4.git\",\"git_push_url\":\"https://gist.github.com/11d35446ecab96c58e8fd14173575eb4.git\",\"html_url\":\"https://gist.github.com/11d35446ecab96c58e8fd14173575eb4\",\"files\":{\"verb14.js\":{\"filename\":\"verb14.js\",\"type\":\"application/javascript\",\"language\":\"JavaScript\",\"raw_url\":\"https://gist.githubusercontent.com/torleifg/11d35446ecab96c58e8fd14173575eb4/raw/7da9ae770a48f75b26d649ebc899d34aed40bd26/verb14.js\",\"size\":1382}},\"public\":true,\"created_at\":\"2019-02-12T07:47:42Z\",\"updated_at\":\"2019-02-12T07:47:54Z\",\"description\":\"\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/11d35446ecab96c58e8fd14173575eb4/comments\",\"owner\":{\"login\":\"torleifg\",\"id\":3832517,\"node_id\":\"MDQ6VXNlcjM4MzI1MTc=\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/3832517?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/torleifg\",\"html_url\":\"https://github.com/torleifg\",\"followers_url\":\"https://api.github.com/users/torleifg/followers\",\"following_url\":\"https://api.github.com/users/torleifg/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/torleifg/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/torleifg/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/torleifg/subscriptions\",\"organizations_url\":\"https://api.github.com/users/torleifg/orgs\",\"repos_url\":\"https://api.github.com/users/torleifg/repos\",\"events_url\":\"https://api.github.com/users/torleifg/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/torleifg/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/f589b08bb0b9a896af269792b1a41d3d\",\"forks_url\":\"https://api.github.com/gists/f589b08bb0b9a896af269792b1a41d3d/forks\",\"commits_url\":\"https://api.github.com/gists/f589b08bb0b9a896af269792b1a41d3d/commits\",\"id\":\"f589b08bb0b9a896af269792b1a41d3d\",\"node_id\":\"MDQ6R2lzdGY1ODliMDhiYjBiOWE4OTZhZjI2OTc5MmIxYTQxZDNk\",\"git_pull_url\":\"https://gist.github.com/f589b08bb0b9a896af269792b1a41d3d.git\",\"git_push_url\":\"https://gist.github.com/f589b08bb0b9a896af269792b1a41d3d.git\",\"html_url\":\"https://gist.github.com/f589b08bb0b9a896af269792b1a41d3d\",\"files\":{\"gistfile1.txt\":{\"filename\":\"gistfile1.txt\",\"type\":\"text/plain\",\"language\":\"Text\",\"raw_url\":\"https://gist.githubusercontent.com/tarasmorskyi/f589b08bb0b9a896af269792b1a41d3d/raw/718cb119895463e7948d658941e75c181111afaf/gistfile1.txt\",\"size\":376}},\"public\":true,\"created_at\":\"2019-02-12T07:47:35Z\",\"updated_at\":\"2019-02-12T07:47:35Z\",\"description\":\"\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/f589b08bb0b9a896af269792b1a41d3d/comments\",\"owner\":{\"login\":\"tarasmorskyi\",\"id\":11429342,\"node_id\":\"MDQ6VXNlcjExNDI5MzQy\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/11429342?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/tarasmorskyi\",\"html_url\":\"https://github.com/tarasmorskyi\",\"followers_url\":\"https://api.github.com/users/tarasmorskyi/followers\",\"following_url\":\"https://api.github.com/users/tarasmorskyi/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/tarasmorskyi/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/tarasmorskyi/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/tarasmorskyi/subscriptions\",\"organizations_url\":\"https://api.github.com/users/tarasmorskyi/orgs\",\"repos_url\":\"https://api.github.com/users/tarasmorskyi/repos\",\"events_url\":\"https://api.github.com/users/tarasmorskyi/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/tarasmorskyi/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/d32d5152ae5945637735ae1fd6c5a538\",\"forks_url\":\"https://api.github.com/gists/d32d5152ae5945637735ae1fd6c5a538/forks\",\"commits_url\":\"https://api.github.com/gists/d32d5152ae5945637735ae1fd6c5a538/commits\",\"id\":\"d32d5152ae5945637735ae1fd6c5a538\",\"node_id\":\"MDQ6R2lzdGQzMmQ1MTUyYWU1OTQ1NjM3NzM1YWUxZmQ2YzVhNTM4\",\"git_pull_url\":\"https://gist.github.com/d32d5152ae5945637735ae1fd6c5a538.git\",\"git_push_url\":\"https://gist.github.com/d32d5152ae5945637735ae1fd6c5a538.git\",\"html_url\":\"https://gist.github.com/d32d5152ae5945637735ae1fd6c5a538\",\"files\":{\"ke1.js\":{\"filename\":\"ke1.js\",\"type\":\"application/javascript\",\"language\":\"JavaScript\",\"raw_url\":\"https://gist.githubusercontent.com/torleifg/d32d5152ae5945637735ae1fd6c5a538/raw/f9d46690d17b90f241b0d71a0edbd9389419e319/ke1.js\",\"size\":3129}},\"public\":true,\"created_at\":\"2019-02-12T07:47:16Z\",\"updated_at\":\"2019-02-12T07:47:26Z\",\"description\":\"\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/d32d5152ae5945637735ae1fd6c5a538/comments\",\"owner\":{\"login\":\"torleifg\",\"id\":3832517,\"node_id\":\"MDQ6VXNlcjM4MzI1MTc=\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/3832517?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/torleifg\",\"html_url\":\"https://github.com/torleifg\",\"followers_url\":\"https://api.github.com/users/torleifg/followers\",\"following_url\":\"https://api.github.com/users/torleifg/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/torleifg/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/torleifg/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/torleifg/subscriptions\",\"organizations_url\":\"https://api.github.com/users/torleifg/orgs\",\"repos_url\":\"https://api.github.com/users/torleifg/repos\",\"events_url\":\"https://api.github.com/users/torleifg/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/torleifg/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/60c3dc4e08b68f52e8aedaca072b15ed\",\"forks_url\":\"https://api.github.com/gists/60c3dc4e08b68f52e8aedaca072b15ed/forks\",\"commits_url\":\"https://api.github.com/gists/60c3dc4e08b68f52e8aedaca072b15ed/commits\",\"id\":\"60c3dc4e08b68f52e8aedaca072b15ed\",\"node_id\":\"MDQ6R2lzdDYwYzNkYzRlMDhiNjhmNTJlOGFlZGFjYTA3MmIxNWVk\",\"git_pull_url\":\"https://gist.github.com/60c3dc4e08b68f52e8aedaca072b15ed.git\",\"git_push_url\":\"https://gist.github.com/60c3dc4e08b68f52e8aedaca072b15ed.git\",\"html_url\":\"https://gist.github.com/60c3dc4e08b68f52e8aedaca072b15ed\",\"files\":{\"employee.csv\":{\"filename\":\"employee.csv\",\"type\":\"text/csv\",\"language\":\"CSV\",\"raw_url\":\"https://gist.githubusercontent.com/bhushi007/60c3dc4e08b68f52e8aedaca072b15ed/raw/57bfa42e8324609f7518d9d90e4cd9cf5e94aaa9/employee.csv\",\"size\":111},\"main.py\":{\"filename\":\"main.py\",\"type\":\"application/x-python\",\"language\":\"Python\",\"raw_url\":\"https://gist.githubusercontent.com/bhushi007/60c3dc4e08b68f52e8aedaca072b15ed/raw/c9d796622ee880dbe620c71b3a4ddef15e53dffb/main.py\",\"size\":1324}},\"public\":true,\"created_at\":\"2019-02-12T07:47:08Z\",\"updated_at\":\"2019-02-12T07:47:08Z\",\"description\":\"employee created by bhushi_007 - https://repl.it/@bhushi_007/employee\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/60c3dc4e08b68f52e8aedaca072b15ed/comments\",\"owner\":{\"login\":\"bhushi007\",\"id\":18232849,\"node_id\":\"MDQ6VXNlcjE4MjMyODQ5\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/18232849?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/bhushi007\",\"html_url\":\"https://github.com/bhushi007\",\"followers_url\":\"https://api.github.com/users/bhushi007/followers\",\"following_url\":\"https://api.github.com/users/bhushi007/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/bhushi007/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/bhushi007/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/bhushi007/subscriptions\",\"organizations_url\":\"https://api.github.com/users/bhushi007/orgs\",\"repos_url\":\"https://api.github.com/users/bhushi007/repos\",\"events_url\":\"https://api.github.com/users/bhushi007/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/bhushi007/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/3d63968709c03eee4d6c0f94088fd223\",\"forks_url\":\"https://api.github.com/gists/3d63968709c03eee4d6c0f94088fd223/forks\",\"commits_url\":\"https://api.github.com/gists/3d63968709c03eee4d6c0f94088fd223/commits\",\"id\":\"3d63968709c03eee4d6c0f94088fd223\",\"node_id\":\"MDQ6R2lzdDNkNjM5Njg3MDljMDNlZWU0ZDZjMGY5NDA4OGZkMjIz\",\"git_pull_url\":\"https://gist.github.com/3d63968709c03eee4d6c0f94088fd223.git\",\"git_push_url\":\"https://gist.github.com/3d63968709c03eee4d6c0f94088fd223.git\",\"html_url\":\"https://gist.github.com/3d63968709c03eee4d6c0f94088fd223\",\"files\":{\"Monaco for Powerline.md\":{\"filename\":\"Monaco for Powerline.md\",\"type\":\"text/plain\",\"language\":\"Markdown\",\"raw_url\":\"https://gist.githubusercontent.com/kevin-de-coninck/3d63968709c03eee4d6c0f94088fd223/raw/ec259fa3ac508f2f994280fa3ffe82144575d2e5/Monaco%20for%20Powerline.md\",\"size\":640},\"Monaco for Powerline.ttf\":{\"filename\":\"Monaco for Powerline.ttf\",\"type\":\"application/x-font-truetype\",\"language\":null,\"raw_url\":\"https://gist.githubusercontent.com/kevin-de-coninck/3d63968709c03eee4d6c0f94088fd223/raw/cd1ee459c84fe460680a3a99d0ce4adb69e2c754/Monaco%20for%20Powerline.ttf\",\"size\":55936},\"screenshot.png\":{\"filename\":\"screenshot.png\",\"type\":\"image/png\",\"language\":null,\"raw_url\":\"https://gist.githubusercontent.com/kevin-de-coninck/3d63968709c03eee4d6c0f94088fd223/raw/135a9f6a191f63f4085c9d8a133662ed5a1fbc78/screenshot.png\",\"size\":86978}},\"public\":true,\"created_at\":\"2019-02-12T07:47:04Z\",\"updated_at\":\"2019-02-12T07:47:04Z\",\"description\":\"Powerline-patched Monaco for Windows and OSX\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/3d63968709c03eee4d6c0f94088fd223/comments\",\"owner\":{\"login\":\"kevin-de-coninck\",\"id\":3583827,\"node_id\":\"MDQ6VXNlcjM1ODM4Mjc=\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/3583827?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/kevin-de-coninck\",\"html_url\":\"https://github.com/kevin-de-coninck\",\"followers_url\":\"https://api.github.com/users/kevin-de-coninck/followers\",\"following_url\":\"https://api.github.com/users/kevin-de-coninck/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/kevin-de-coninck/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/kevin-de-coninck/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/kevin-de-coninck/subscriptions\",\"organizations_url\":\"https://api.github.com/users/kevin-de-coninck/orgs\",\"repos_url\":\"https://api.github.com/users/kevin-de-coninck/repos\",\"events_url\":\"https://api.github.com/users/kevin-de-coninck/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/kevin-de-coninck/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/b5cb7a7b6e0f218fc5ad05e82bcdbc66\",\"forks_url\":\"https://api.github.com/gists/b5cb7a7b6e0f218fc5ad05e82bcdbc66/forks\",\"commits_url\":\"https://api.github.com/gists/b5cb7a7b6e0f218fc5ad05e82bcdbc66/commits\",\"id\":\"b5cb7a7b6e0f218fc5ad05e82bcdbc66\",\"node_id\":\"MDQ6R2lzdGI1Y2I3YTdiNmUwZjIxOGZjNWFkMDVlODJiY2RiYzY2\",\"git_pull_url\":\"https://gist.github.com/b5cb7a7b6e0f218fc5ad05e82bcdbc66.git\",\"git_push_url\":\"https://gist.github.com/b5cb7a7b6e0f218fc5ad05e82bcdbc66.git\",\"html_url\":\"https://gist.github.com/b5cb7a7b6e0f218fc5ad05e82bcdbc66\",\"files\":{\"untrusted-lvl5-solution.js\":{\"filename\":\"untrusted-lvl5-solution.js\",\"type\":\"application/javascript\",\"language\":\"JavaScript\",\"raw_url\":\"https://gist.githubusercontent.com/Untrusted-Game/b5cb7a7b6e0f218fc5ad05e82bcdbc66/raw/722f7812c1ede6b74f37e21fda92bed94d132590/untrusted-lvl5-solution.js\",\"size\":1272}},\"public\":true,\"created_at\":\"2019-02-12T07:46:53Z\",\"updated_at\":\"2019-02-12T07:46:53Z\",\"description\":\"Solution to level 5 in Untrusted: http://alex.nisnevich.com/untrusted/\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/b5cb7a7b6e0f218fc5ad05e82bcdbc66/comments\",\"owner\":{\"login\":\"Untrusted-Game\",\"id\":37789057,\"node_id\":\"MDQ6VXNlcjM3Nzg5MDU3\",\"avatar_url\":\"https://avatars2.githubusercontent.com/u/37789057?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/Untrusted-Game\",\"html_url\":\"https://github.com/Untrusted-Game\",\"followers_url\":\"https://api.github.com/users/Untrusted-Game/followers\",\"following_url\":\"https://api.github.com/users/Untrusted-Game/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/Untrusted-Game/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/Untrusted-Game/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/Untrusted-Game/subscriptions\",\"organizations_url\":\"https://api.github.com/users/Untrusted-Game/orgs\",\"repos_url\":\"https://api.github.com/users/Untrusted-Game/repos\",\"events_url\":\"https://api.github.com/users/Untrusted-Game/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/Untrusted-Game/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/6a497e10237f6320ded20a9d2e146911\",\"forks_url\":\"https://api.github.com/gists/6a497e10237f6320ded20a9d2e146911/forks\",\"commits_url\":\"https://api.github.com/gists/6a497e10237f6320ded20a9d2e146911/commits\",\"id\":\"6a497e10237f6320ded20a9d2e146911\",\"node_id\":\"MDQ6R2lzdDZhNDk3ZTEwMjM3ZjYzMjBkZWQyMGE5ZDJlMTQ2OTEx\",\"git_pull_url\":\"https://gist.github.com/6a497e10237f6320ded20a9d2e146911.git\",\"git_push_url\":\"https://gist.github.com/6a497e10237f6320ded20a9d2e146911.git\",\"html_url\":\"https://gist.github.com/6a497e10237f6320ded20a9d2e146911\",\"files\":{\".vimrc\":{\"filename\":\".vimrc\",\"type\":\"text/plain\",\"language\":\"Vim script\",\"raw_url\":\"https://gist.githubusercontent.com/ayealexadams/6a497e10237f6320ded20a9d2e146911/raw/e14cc3ee9336180de04b1f76abcef08f5ac7ba9e/.vimrc\",\"size\":128}},\"public\":true,\"created_at\":\"2019-02-12T07:46:46Z\",\"updated_at\":\"2019-02-12T07:46:46Z\",\"description\":\"Vim Config\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/6a497e10237f6320ded20a9d2e146911/comments\",\"owner\":{\"login\":\"ayealexadams\",\"id\":16046335,\"node_id\":\"MDQ6VXNlcjE2MDQ2MzM1\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/16046335?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/ayealexadams\",\"html_url\":\"https://github.com/ayealexadams\",\"followers_url\":\"https://api.github.com/users/ayealexadams/followers\",\"following_url\":\"https://api.github.com/users/ayealexadams/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/ayealexadams/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/ayealexadams/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/ayealexadams/subscriptions\",\"organizations_url\":\"https://api.github.com/users/ayealexadams/orgs\",\"repos_url\":\"https://api.github.com/users/ayealexadams/repos\",\"events_url\":\"https://api.github.com/users/ayealexadams/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/ayealexadams/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/728542f9085797afe38aaa7f893dddea\",\"forks_url\":\"https://api.github.com/gists/728542f9085797afe38aaa7f893dddea/forks\",\"commits_url\":\"https://api.github.com/gists/728542f9085797afe38aaa7f893dddea/commits\",\"id\":\"728542f9085797afe38aaa7f893dddea\",\"node_id\":\"MDQ6R2lzdDcyODU0MmY5MDg1Nzk3YWZlMzhhYWE3Zjg5M2RkZGVh\",\"git_pull_url\":\"https://gist.github.com/728542f9085797afe38aaa7f893dddea.git\",\"git_push_url\":\"https://gist.github.com/728542f9085797afe38aaa7f893dddea.git\",\"html_url\":\"https://gist.github.com/728542f9085797afe38aaa7f893dddea\",\"files\":{\"playground.rs\":{\"filename\":\"playground.rs\",\"type\":\"text/plain\",\"language\":\"Rust\",\"raw_url\":\"https://gist.githubusercontent.com/rust-play/728542f9085797afe38aaa7f893dddea/raw/3a561b9d6b304d958b1933aaaafc445752c2d5ad/playground.rs\",\"size\":148}},\"public\":true,\"created_at\":\"2019-02-12T07:46:44Z\",\"updated_at\":\"2019-02-12T07:46:45Z\",\"description\":\"Code shared from the Rust Playground\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/728542f9085797afe38aaa7f893dddea/comments\",\"owner\":{\"login\":\"rust-play\",\"id\":37046162,\"node_id\":\"MDQ6VXNlcjM3MDQ2MTYy\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/37046162?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/rust-play\",\"html_url\":\"https://github.com/rust-play\",\"followers_url\":\"https://api.github.com/users/rust-play/followers\",\"following_url\":\"https://api.github.com/users/rust-play/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/rust-play/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/rust-play/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/rust-play/subscriptions\",\"organizations_url\":\"https://api.github.com/users/rust-play/orgs\",\"repos_url\":\"https://api.github.com/users/rust-play/repos\",\"events_url\":\"https://api.github.com/users/rust-play/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/rust-play/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/8988ff8430e7a1cd6bc677d07da08917\",\"forks_url\":\"https://api.github.com/gists/8988ff8430e7a1cd6bc677d07da08917/forks\",\"commits_url\":\"https://api.github.com/gists/8988ff8430e7a1cd6bc677d07da08917/commits\",\"id\":\"8988ff8430e7a1cd6bc677d07da08917\",\"node_id\":\"MDQ6R2lzdDg5ODhmZjg0MzBlN2ExY2Q2YmM2NzdkMDdkYTA4OTE3\",\"git_pull_url\":\"https://gist.github.com/8988ff8430e7a1cd6bc677d07da08917.git\",\"git_push_url\":\"https://gist.github.com/8988ff8430e7a1cd6bc677d07da08917.git\",\"html_url\":\"https://gist.github.com/8988ff8430e7a1cd6bc677d07da08917\",\"files\":{\"tt1.js\":{\"filename\":\"tt1.js\",\"type\":\"application/javascript\",\"language\":\"JavaScript\",\"raw_url\":\"https://gist.githubusercontent.com/torleifg/8988ff8430e7a1cd6bc677d07da08917/raw/3dc2b416c347ce3dbcd4f027f6146f566cebfe2d/tt1.js\",\"size\":2002}},\"public\":true,\"created_at\":\"2019-02-12T07:46:37Z\",\"updated_at\":\"2019-02-12T07:46:48Z\",\"description\":\"\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/8988ff8430e7a1cd6bc677d07da08917/comments\",\"owner\":{\"login\":\"torleifg\",\"id\":3832517,\"node_id\":\"MDQ6VXNlcjM4MzI1MTc=\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/3832517?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/torleifg\",\"html_url\":\"https://github.com/torleifg\",\"followers_url\":\"https://api.github.com/users/torleifg/followers\",\"following_url\":\"https://api.github.com/users/torleifg/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/torleifg/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/torleifg/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/torleifg/subscriptions\",\"organizations_url\":\"https://api.github.com/users/torleifg/orgs\",\"repos_url\":\"https://api.github.com/users/torleifg/repos\",\"events_url\":\"https://api.github.com/users/torleifg/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/torleifg/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/29777291879cc6c326f9783a0a2ec1ac\",\"forks_url\":\"https://api.github.com/gists/29777291879cc6c326f9783a0a2ec1ac/forks\",\"commits_url\":\"https://api.github.com/gists/29777291879cc6c326f9783a0a2ec1ac/commits\",\"id\":\"29777291879cc6c326f9783a0a2ec1ac\",\"node_id\":\"MDQ6R2lzdDI5Nzc3MjkxODc5Y2M2YzMyNmY5NzgzYTBhMmVjMWFj\",\"git_pull_url\":\"https://gist.github.com/29777291879cc6c326f9783a0a2ec1ac.git\",\"git_push_url\":\"https://gist.github.com/29777291879cc6c326f9783a0a2ec1ac.git\",\"html_url\":\"https://gist.github.com/29777291879cc6c326f9783a0a2ec1ac\",\"files\":{\"UuidTrait.php\":{\"filename\":\"UuidTrait.php\",\"type\":\"application/x-httpd-php\",\"language\":\"PHP\",\"raw_url\":\"https://gist.githubusercontent.com/DavidNgugi/29777291879cc6c326f9783a0a2ec1ac/raw/a029f749e624ff17f4bdc6492428c961fef1a827/UuidTrait.php\",\"size\":358}},\"public\":true,\"created_at\":\"2019-02-12T07:46:36Z\",\"updated_at\":\"2019-02-12T07:46:36Z\",\"description\":\"UUID trait\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/29777291879cc6c326f9783a0a2ec1ac/comments\",\"owner\":{\"login\":\"DavidNgugi\",\"id\":5968269,\"node_id\":\"MDQ6VXNlcjU5NjgyNjk=\",\"avatar_url\":\"https://avatars1.githubusercontent.com/u/5968269?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/DavidNgugi\",\"html_url\":\"https://github.com/DavidNgugi\",\"followers_url\":\"https://api.github.com/users/DavidNgugi/followers\",\"following_url\":\"https://api.github.com/users/DavidNgugi/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/DavidNgugi/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/DavidNgugi/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/DavidNgugi/subscriptions\",\"organizations_url\":\"https://api.github.com/users/DavidNgugi/orgs\",\"repos_url\":\"https://api.github.com/users/DavidNgugi/repos\",\"events_url\":\"https://api.github.com/users/DavidNgugi/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/DavidNgugi/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/a55a82ad19bfd6b41e1902917d1ba2df\",\"forks_url\":\"https://api.github.com/gists/a55a82ad19bfd6b41e1902917d1ba2df/forks\",\"commits_url\":\"https://api.github.com/gists/a55a82ad19bfd6b41e1902917d1ba2df/commits\",\"id\":\"a55a82ad19bfd6b41e1902917d1ba2df\",\"node_id\":\"MDQ6R2lzdGE1NWE4MmFkMTliZmQ2YjQxZTE5MDI5MTdkMWJhMmRm\",\"git_pull_url\":\"https://gist.github.com/a55a82ad19bfd6b41e1902917d1ba2df.git\",\"git_push_url\":\"https://gist.github.com/a55a82ad19bfd6b41e1902917d1ba2df.git\",\"html_url\":\"https://gist.github.com/a55a82ad19bfd6b41e1902917d1ba2df\",\"files\":{\"app.vue\":{\"filename\":\"app.vue\",\"type\":\"text/plain\",\"language\":\"Vue\",\"raw_url\":\"https://gist.githubusercontent.com/bhaidar/a55a82ad19bfd6b41e1902917d1ba2df/raw/834835a5b0e6276ec36bd8501f4c17737b987a34/app.vue\",\"size\":11187}},\"public\":true,\"created_at\":\"2019-02-12T07:46:27Z\",\"updated_at\":\"2019-02-12T07:46:27Z\",\"description\":\"todoapp\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/a55a82ad19bfd6b41e1902917d1ba2df/comments\",\"owner\":{\"login\":\"bhaidar\",\"id\":1163421,\"node_id\":\"MDQ6VXNlcjExNjM0MjE=\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/1163421?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/bhaidar\",\"html_url\":\"https://github.com/bhaidar\",\"followers_url\":\"https://api.github.com/users/bhaidar/followers\",\"following_url\":\"https://api.github.com/users/bhaidar/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/bhaidar/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/bhaidar/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/bhaidar/subscriptions\",\"organizations_url\":\"https://api.github.com/users/bhaidar/orgs\",\"repos_url\":\"https://api.github.com/users/bhaidar/repos\",\"events_url\":\"https://api.github.com/users/bhaidar/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/bhaidar/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false},{\"url\":\"https://api.github.com/gists/c7a3ff2bd20cff4634c938f8bd720cd6\",\"forks_url\":\"https://api.github.com/gists/c7a3ff2bd20cff4634c938f8bd720cd6/forks\",\"commits_url\":\"https://api.github.com/gists/c7a3ff2bd20cff4634c938f8bd720cd6/commits\",\"id\":\"c7a3ff2bd20cff4634c938f8bd720cd6\",\"node_id\":\"MDQ6R2lzdGM3YTNmZjJiZDIwY2ZmNDYzNGM5MzhmOGJkNzIwY2Q2\",\"git_pull_url\":\"https://gist.github.com/c7a3ff2bd20cff4634c938f8bd720cd6.git\",\"git_push_url\":\"https://gist.github.com/c7a3ff2bd20cff4634c938f8bd720cd6.git\",\"html_url\":\"https://gist.github.com/c7a3ff2bd20cff4634c938f8bd720cd6\",\"files\":{\"load-my-script-pmpro.php\":{\"filename\":\"load-my-script-pmpro.php\",\"type\":\"application/x-httpd-php\",\"language\":\"PHP\",\"raw_url\":\"https://gist.githubusercontent.com/travislima/c7a3ff2bd20cff4634c938f8bd720cd6/raw/bc11e433fb1ab4819f7e9604b12ad20fb84553d8/load-my-script-pmpro.php\",\"size\":468}},\"public\":true,\"created_at\":\"2019-02-12T07:46:19Z\",\"updated_at\":\"2019-02-12T07:46:19Z\",\"description\":\"Load content on confirmation or checkout page for PMPro.\",\"comments\":0,\"user\":null,\"comments_url\":\"https://api.github.com/gists/c7a3ff2bd20cff4634c938f8bd720cd6/comments\",\"owner\":{\"login\":\"travislima\",\"id\":18071960,\"node_id\":\"MDQ6VXNlcjE4MDcxOTYw\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/18071960?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/travislima\",\"html_url\":\"https://github.com/travislima\",\"followers_url\":\"https://api.github.com/users/travislima/followers\",\"following_url\":\"https://api.github.com/users/travislima/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/travislima/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/travislima/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/travislima/subscriptions\",\"organizations_url\":\"https://api.github.com/users/travislima/orgs\",\"repos_url\":\"https://api.github.com/users/travislima/repos\",\"events_url\":\"https://api.github.com/users/travislima/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/travislima/received_events\",\"type\":\"User\",\"site_admin\":false},\"truncated\":false}]";
        ONode.ofJson(json);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            ONode.ofJson(json);

            //assert "1".equals(c.node.get("data").get("list").get(0).get("id").getString());
        }

        System.out.println(System.currentTimeMillis() - start);

    }

    /**
     * 测试性能（小json）
     */
    @Test
    public void test0() throws Exception {
        //100000=>950,931,939    //100=>.95 //1462,1456,1459 //1890,1808,1768//1600,1669,1673
        //
        //1kb
        //
        String json = "{\"code\":1,\"msg\":\"Succeed\",\"data\":{\"list\":[{\"name\":\"北京'\\ud83d\\udc4c\\t\\n\",\"id\":1},{\"name\":\"新疆\",\"id\":31},{\"name\":\"重庆\",\"id\":4},{\"name\":\"广东\",\"id\":19},{\"name\":\"浙江\",\"id\":15},{\"name\":\"天津\",\"id\":3},{\"name\":\"港澳\",\"id\":52993},{\"name\":\"广西\",\"id\":20},{\"name\":\"内蒙古\",\"id\":11},{\"name\":\"宁夏\",\"id\":30},{\"name\":\"江西\",\"id\":21},{\"name\":\"台湾\",\"id\":32},{\"name\":\"安徽\",\"id\":14},{\"name\":\"贵州\",\"id\":24},{\"name\":\"陕西\",\"id\":27},{\"name\":\"辽宁\",\"id\":8},{\"name\":\"山西\",\"id\":6},{\"name\":\"青海\",\"id\":29},{\"name\":\"四川\",\"id\":22},{\"name\":\"江苏\",\"id\":12},{\"name\":\"河北\",\"id\":5},{\"name\":\"西藏\",\"id\":26},{\"name\":\"钓鱼岛\",\"id\":84},{\"name\":\"福建\",\"id\":16},{\"name\":\"吉林\",\"id\":9},{\"name\":\"湖北\",\"id\":17},{\"name\":\"云南\",\"id\":25},{\"name\":\"海南\",\"id\":23},{\"name\":\"上海\",\"id\":2},{\"name\":\"甘肃\",\"id\":28},{\"name\":\"湖南\",\"id\":18},{\"name\":\"山东\",\"id\":13},{\"name\":\"河南\",\"id\":7},{\"name\":\"黑龙江\",\"id\":10}]}}";
        ONode.ofJson(json);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            ONode.ofJson(json);

            //assert "1".equals(c.node.get("data").get("list").get(0).get("id").getString());
        }

        System.out.println(System.currentTimeMillis() - start);

    }

    @Test
    public void test1() throws Exception {
        //1000,000=>1695,1713,1738 //2994,2960,2801 //4350,4418,4356
        //
        //
        Map<String, Object> obj = new LinkedHashMap<>();

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", true);
        m.put("c", 1.2);
        m.put("d", new Date());

        list.add(m);

        obj.put("list", list);

        Options options = Options.of(Feature.Write_ClassName, Feature.Write_BrowserCompatible,
                Feature.Read_AllowUseGetter, Feature.Write_AllowUseSetter);

        String tmp = ONode.ofBean(obj, options).toJson();
        System.out.println(tmp);

        long start = System.currentTimeMillis();
        for (int i = 0, len = 1000000; i < len; i++) {
            ONode.ofBean(obj, options).toJson();
        }
        long times = System.currentTimeMillis() - start;

        System.out.println(times);

        assert times > 0;
    }

    @Test
    public void test2() throws Exception {
        //100000=>1473,1493,1494 //2367,2367,2441 //4147,4115,4090
        //
        //
        UserGroupModel group = new UserGroupModel();
        group.id = 9999;
        group.users = new ArrayList<>();
        group.users2 = new LinkedHashMap<>();
        group.users3 = new TreeSet<>();
        group.names = new String[5];
        group.ids = new short[5];
        group.iids = new Integer[5];

        for (short i = 0; i < 5; i++) {
            UserModel user = new UserModel();
            user.id = i;
            user.name = "张三" + i;
            user.note = null;
            group.users.add(user);
            group.users2.put(Integer.valueOf(i), user);
            group.names[i] = "李四" + i;
            group.ids[i] = i;
        }

        Options options = Options.of(Feature.Write_ClassName, Feature.Write_BrowserCompatible,
                Feature.Read_AllowUseGetter, Feature.Write_AllowUseSetter);

        String tmp = ONode.ofBean(group, options).toJson();
        System.out.println(tmp);

        long start = System.currentTimeMillis();
        for (int i = 0, len = 100000; i < len; i++) {
            ONode.ofBean(group, options).toJson();
        }
        long times = System.currentTimeMillis() - start;

        System.out.println(times);

        assert times > 0;

    }


    @Test
    public void test11() throws Exception {
        //
        //10000000=>72s,71s,72s
        //1000000=>7.9s,7.8s
        //100000=>1.6s,1.6s,1.5s //2976,2934,2917 //3160,3212,3174
        //
        UserGroupModel group = new UserGroupModel();
        group.id = 9999;
        group.users = new ArrayList<>();
        group.users2 = new LinkedHashMap<>();
        group.names = new String[5];
        group.ids = new short[5];
        group.iids = new Integer[5];

        for (short i = 0; i < 5; i++) {
            UserModel user = new UserModel();
            user.id = i;
            user.name = "张三" + i;
            user.note = null;
            group.users.add(user);
            group.users2.put(Integer.valueOf(i), user);
            group.names[i] = "李四" + i;
            group.ids[i] = i;
        }

        Options options = Options.of(Feature.Write_ClassName, Feature.Write_BrowserCompatible,
                Feature.Read_AutoType,
                Feature.Read_AllowUseGetter, Feature.Write_AllowUseSetter);

        String json = ONode.ofBean(group, options).toJson();
        System.out.println(json);

        ONode.ofJson(json, options).toBean(UserGroupModel.class);

        long start = System.currentTimeMillis();
        for (int i = 0, len = 100000; i < len; i++) {
            ONode.ofJson(json, options).toBean(UserGroupModel.class);
        }
        long times = System.currentTimeMillis() - start;

        System.out.println(times);

        assert times > 0;

    }

    @Test
    public void test11_x() throws Exception {
        //
        //10000000=>6.3s,6.4s
        //1000000=>6.8s
        //100000=>958  //1385,1394,1392 //2799,2840,2744//2488,2508,2450
        //
        UserGroupModel group = new UserGroupModel();
        group.id = 9999;
        group.users = new ArrayList<>();
        group.users2 = new LinkedHashMap<>();
        group.names = new String[5];
        group.ids = new short[5];
        group.iids = new Integer[5];

        for (short i = 0; i < 5; i++) {
            UserModel user = new UserModel();
            user.id = i;
            user.name = "张三" + i;
            user.note = null;
            group.users.add(user);
            group.users2.put(Integer.valueOf(i), user);
            group.names[i] = "李四" + i;
            group.ids[i] = i;
        }

        Options options = Options.of();

        String json = ONode.ofBean(group, options).toJson();
        System.out.println(json);

        ONode.ofJson(json, options).toBean(Object.class);

        long start = System.currentTimeMillis();
        for (int i = 0, len = 100000; i < len; i++) {
            ONode.ofJson(json, options).toBean(Object.class);
        }
        long times = System.currentTimeMillis() - start;

        System.out.println(times);

        assert times > 0;

    }


    @Test
    public void test11_2() throws Exception {
        //
        //10000000=>19s,19s
        //1000000=>3.1s
        //100000=>1.2s  //956,909,911 //1028,1035,1020
        //
        UserGroupModel group = new UserGroupModel();
        group.id = 9999;
        group.users = new ArrayList<>();
        group.users2 = new LinkedHashMap<>();
        group.names = new String[5];
        group.ids = new short[5];
        group.iids = new Integer[5];

        for (short i = 0; i < 5; i++) {
            UserModel user = new UserModel();
            user.id = i;
            user.name = "张三" + i;
            user.note = null;
            group.users.add(user);
            group.users2.put(Integer.valueOf(i), user);
            group.names[i] = "李四" + i;
            group.ids[i] = i;
        }


        Options options = Options.of(Feature.Read_AllowUseGetter, Feature.Write_AllowUseSetter);

        String json = ONode.ofBean(group, options).toJson();
        System.out.println(json);

        ONode tmp = ONode.ofJson(json, options);

        tmp.toBean(UserGroupModel.class);

        long start = System.currentTimeMillis();
        for (int i = 0, len = 100000; i < len; i++) {
            tmp.toBean(UserGroupModel.class);
        }
        long times = System.currentTimeMillis() - start;

        System.out.println(times);

        assert times > 0;

    }

    @Test
    public void test11_3() throws Exception {
        //
        //10000000=>9.6s,9.7s
        //1000000=>1.3s
        //100000=>161,166,163 //837,858,824 //1528,1618,1519
        //
        UserGroupModel group = new UserGroupModel();
        group.id = 9999;
        group.users = new ArrayList<>();
        group.users2 = new LinkedHashMap<>();
        group.names = new String[5];
        group.ids = new short[5];
        group.iids = new Integer[5];

        for (short i = 0; i < 5; i++) {
            UserModel user = new UserModel();
            user.id = i;
            user.name = "张三" + i;
            user.note = null;
            group.users.add(user);
            group.users2.put(Integer.valueOf(i), user);
            group.names[i] = "李四" + i;
            group.ids[i] = i;
        }

        String json = ONode.ofBean(group).toJson();
        System.out.println(json);

        ONode tmp = ONode.ofJson(json);

        tmp.toBean(Object.class);

        long start = System.currentTimeMillis();
        for (int i = 0, len = 100000; i < len; i++) {
            tmp.toBean(Object.class);
        }
        long times = System.currentTimeMillis() - start;

        System.out.println(times);

        assert times > 0;

    }


    @Test
    public void test12() throws Exception {
        //100000=>433,403,424  //502,409,445 //819,866,973
        //
        //
        Map<String, Object> obj = new LinkedHashMap<String, Object>();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = new LinkedHashMap<String, Object>();
        m.put("a", 1);
        m.put("b", true);
        m.put("c", 1.2);
        m.put("d", new Date());

        list.add(m);

        obj.put("list", list);

        Options options = Options.of(Feature.Read_AllowUseGetter, Feature.Write_AllowUseSetter);

        String json = ONode.ofBean(obj, options).toJson();
        System.out.println(json);

        ONode.ofJson(json, options).toBean(obj.getClass());


        long start = System.currentTimeMillis();
        for (int i = 0, len = 100000; i < len; i++) {
            ONode.ofJson(json, options).toBean(obj.getClass());
        }
        long times = System.currentTimeMillis() - start;

        System.out.println(times);

        assert times > 0;

    }

    @Test
    public void test12_1() throws Exception {
        //100000=>168,163,186 //233,250,267 //555,556,546
        //
        //
        Map<String, Object> obj = new LinkedHashMap<String, Object>();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = new LinkedHashMap<String, Object>();
        m.put("a", 1);
        m.put("b", true);
        m.put("c", 1.2);
        m.put("d", new Date());

        list.add(m);

        obj.put("list", list);

        String json = ONode.ofBean(obj).toJson();
        System.out.println(json);


        long start = System.currentTimeMillis();
        for (int i = 0, len = 100000; i < len; i++) {
            ONode.ofJson(json);
        }
        long times = System.currentTimeMillis() - start;

        System.out.println(times);

        assert times > 0;

    }

    @Test
    public void test12_2() throws Exception {
        //100000=>168,163,186 //333,341,335 //477,408,414
        //
        //
        Map<String, Object> obj = new LinkedHashMap<String, Object>();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = new LinkedHashMap<String, Object>();
        m.put("a", 1);
        m.put("b", true);
        m.put("c", 1.2);
        m.put("d", new Date());

        list.add(m);

        obj.put("list", list);

        Options options = Options.of(Feature.Read_AllowUseGetter, Feature.Write_AllowUseSetter);

        String json = ONode.ofBean(obj, options).toJson();
        System.out.println(json);
        ONode n = ONode.ofJson(json, options);

        long start = System.currentTimeMillis();
        for (int i = 0, len = 100000; i < len; i++) {
            n.toBean(obj.getClass());
        }
        long times = System.currentTimeMillis() - start;

        System.out.println(times);

        assert times > 0;

    }
}
