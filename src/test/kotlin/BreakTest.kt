import org.junit.jupiter.api.Test

class BreakTest {

    @Test
    fun `loop break`() {
        scriptAssert("""
            a = 0
            
            while(a != 45646545646) do
                if a == 3 then
                    break
                end
            
                a += 1
            end
            
            j = 0
            for i=0,45646545646 do
                if i == 3 then
                    break
                end
            
                j += 1
            end
            
            tab = [0,1,2,3,4,5,6,7,8,9]
                        
            r = 0
            
            for val in tab do
                if val == 5 then
                    break
                end
                r += 1
            end
            
            return a + j + r
        """.trimIndent(), 11)
    }

}
